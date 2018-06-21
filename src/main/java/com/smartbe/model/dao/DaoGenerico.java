/*
* The MIT License
* 
* Copyright: Copyright (C) 2017 T2Ti.COM
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
* 
* The author may be contacted at: t2ti.com@gmail.com
*
* @author Claudio de Barros (T2Ti.com)
* 
 */
package com.smartbe.model.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.primefaces.model.SortOrder;

public class DaoGenerico<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Class<T> clazz;
    protected EntityManager em;
    private boolean autoCommit = true;

    public DaoGenerico(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected EntityManager abrirConexao() throws Exception {
        if (em == null || !em.isOpen()) {
            em = T2TiEntityManagerFactory.createEntityManager();
            em.getTransaction().begin();
        }
        return em;
    }

    public void fecharConexao() throws Exception {
        if (em != null && em.isOpen()) {
            try {
                if (em.getTransaction() != null && em.getTransaction().isActive()) {
                    if (em.getTransaction().getRollbackOnly()) {
                        em.getTransaction().rollback();
                    } else {                    	
                        em.getTransaction().commit();
                    }
                }
            } catch (Exception e) {
                if (em.getTransaction() != null && em.getTransaction().isActive()) {
                	em.close();
                	abrirConexao();
                    em.getTransaction().rollback();
                }
                throw e;
            } finally {
                em.close();
            }
        }
    }

    public void persist(T bean) throws Exception {
        try {
            abrirConexao();
            em.persist(bean);
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public T merge(T bean) throws Exception {
        try {
            abrirConexao();
            return em.merge(bean);
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public void excluir(T bean) throws Exception {
        try {
            abrirConexao();
            bean = em.merge(bean);
            em.remove(bean);
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public void excluir(T bean, Integer id) throws Exception {
        try {
            abrirConexao();
            em.remove(em.getReference(bean.getClass(), id));
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public T getBean(Integer id) throws Exception {
        try {
            abrirConexao();
            return (T) em.find(clazz, id);
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public T getBeanJoinFetch(Integer id) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT DISTINCT o FROM " + clazz.getName() + " o";
            Field fields[] = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.getType() == Set.class) {
                    jpql += " LEFT JOIN FETCH o." + f.getName();
                }
            }

            jpql += " WHERE o.id = :id";

            Query query = em.createQuery(jpql);
            query.setParameter("id", id);

            return (T) query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public T getBean(String atributo, Object valor) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT o FROM " + clazz.getName() + " o WHERE o." + atributo + " = :valor";
            Query query = em.createQuery(jpql);
            query.setParameter("valor", valor);
            return (T) query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public T getBean(String atributo1, Object valor1, String atributo2, Object valor2) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT o FROM " + clazz.getName() + " o WHERE o." + atributo1 + " = :valor1 AND o." + atributo2 + " = :valor2";
            Query query = em.createQuery(jpql);
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            return (T) query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public List<T> getBeans() throws Exception {
        try {
            abrirConexao();
            CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(clazz);
            Root<T> bean = criteria.from(clazz);
            criteria.select(bean);
            return em.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public List<T> getBeans(String atributo, Object valor) throws Exception {
        try {
            abrirConexao();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            Root<T> root = criteria.from(clazz);
            criteria.select(root);

            if (valor.getClass() == String.class) {
                Path<String> nome = root.get(atributo);
                criteria.where(builder.equal(nome, valor));
            } else if (valor.getClass() == Integer.class) {
                Path<Integer> nome = root.get(atributo);
                criteria.where(builder.equal(nome, valor));
            } else if (valor.getClass() == Date.class) {
                Path<Date> nome = root.get(atributo);
                criteria.where(builder.equal(nome, valor));
            } else if (valor.getClass() == BigDecimal.class) {
                Path<BigDecimal> nome = root.get(atributo);
                criteria.where(builder.equal(nome, valor));
            }

            TypedQuery<T> query = em.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public List<T> listaBeans(String atributo, Object valor) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT o FROM " + clazz.getName() + " o WHERE o." + atributo + " = :valor";
            Query query = em.createQuery(jpql);
            query.setParameter("valor", valor);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }
    
    public List<T> getBeansLike(String atributo, String valor, Integer maxResult) throws Exception {
        try {
            abrirConexao();
            Query query = em.createQuery("SELECT o FROM " + clazz.getName() + " o WHERE LOWER(o." + atributo + ") like :valor");
            query.setParameter("valor", "%" + valor.trim().toLowerCase() + "%");
            query.setMaxResults(maxResult == null ? 10 : maxResult);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }
    
    public List<T> getBeansLike(Class<T> clazz, String atributo, String valor) throws Exception {
		Query query = em.createQuery("SELECT o FROM " + clazz.getName() + " o WHERE LOWER(o." + atributo + ") like :valor");
		query.setParameter("valor", "%" + valor.trim().toLowerCase() + "%");
		query.setMaxResults(100);
		List<T> beans = query.getResultList();
		return beans;
	}

    public List<T> getBeansLike(String atributo, String valor) throws Exception {
        return getBeansLike(atributo, valor, null);
    }
    
    public Long getTotalRegistros(Map<String, Object> filters) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT COUNT(o.id) FROM " + clazz.getName() + " o WHERE 1 = 1";

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String atributo = it.next();
                Object valor = filters.get(atributo);
                if (valor != null) {
                    if (valor.getClass() == String.class) {
                        jpql += " AND LOWER(o." + atributo + ") like :" + atributo;
                    } else {
                        jpql += " AND o." + atributo + " = :" + atributo;
                    }
                }
            }

            Query query = em.createQuery(jpql);

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String atributo = it.next();
                Object valor = filters.get(atributo);
                if (valor != null) {
                    if (valor.getClass() == String.class) {
                        query.setParameter(atributo, "%" + String.valueOf(valor).toLowerCase() + "%");
                    } else {
                        query.setParameter(atributo, valor);
                    }
                }
            }
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }

    public List<T> getBeans(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
        try {
            abrirConexao();
            String jpql = "SELECT o FROM " + clazz.getName() + " o WHERE 1 = 1";

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String atributo = it.next();
                Object valor = filters.get(atributo);
                if (valor != null) {
                    if (valor.getClass() == String.class) {
                        jpql += " AND LOWER(o." + atributo + ") like :" + atributo;
                    } else {
                        jpql += " AND o." + atributo + " = :" + atributo;
                    }
                }
            }

            if (sortField != null && sortOrder != null) {
                if (sortOrder.equals(SortOrder.ASCENDING)) {
                    jpql += " ORDER BY o." + sortField + " ASC";
                } else if (sortOrder.equals(SortOrder.DESCENDING)) {
                    jpql += " ORDER BY o." + sortField + " DESC";
                }
            }

            Query query = em.createQuery(jpql);

            for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                String atributo = it.next();
                Object valor = filters.get(atributo);
                if (valor != null) {
                    if (valor.getClass() == String.class) {
                        query.setParameter(atributo, "%" + String.valueOf(valor).toLowerCase() + "%");
                    } else {
                        query.setParameter(atributo, valor);
                    }
                }
            }

            query.setFirstResult(first);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            if (isAutoCommit()) {
                fecharConexao();
            }
        }
    }
    
    

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

}
