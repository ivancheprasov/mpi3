package main;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static java.lang.Math.pow;

public class DotDAOImpl implements DotDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private Properties properties = PropertiesLoader.getProperties();
    public void activateComponent() {

        Map<String, String> map = new HashMap<>();
        PersistenceProvider persistenceProvider = new PersistenceProvider();
        map.put("javax.persistence.jdbc.driver", org.postgresql.Driver.class.getName());
        map.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/postgres");
        map.put("javax.persistence.jdbc.user", getProperty("USER"));
        map.put("javax.persistence.jdbc.password", getProperty("PASS"));
        map.put("eclipselink.ddl-generation", "create-or-extend-tables");
        map.put("eclipselink.ddl-generation.output-mode", "database");
        map.put("eclipselink.logging.level", "FINE");
        map.put("eclipselink.allow-zero-id", "true");
//        PersistenceUnitProperties.
        EntityManagerFactory emf = persistenceProvider.createContainerEntityManagerFactory(new PersistenceUnitInfo(){
            @Override
            public String getPersistenceUnitName() {
                return  "unit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return org.eclipse.persistence.jpa.PersistenceProvider.class.getName();
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                try {
                    return new File(".").toURL();
                } catch (Exception e){
                    return null;
                }
            }

            @Override
            public List<String> getManagedClassNames() {
                return Arrays.asList(Dot.class.getName());
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return ClassLoader.getSystemClassLoader();
            }

            @Override
            public void addTransformer(ClassTransformer classTransformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        }, map);
//        map.put(PersistenceUnitProperties.CLASSLOADER, getClass().getClassLoader());
//        if(System.getProperty("os.name").contains("Windows")) {
//            entityManagerFactory = Persistence.createEntityManagerFactory("postgresql-eclipselink", map);
//        } else {
//            entityManagerFactory = Persistence.createEntityManagerFactory("postgresql-eclipselink-helios", map);
//        }
        entityManager = emf.createEntityManager();
    }

    public void deactivateComponent() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
        entityManager = null;
        entityManagerFactory = null;
    }

    public DotDAOImpl() {
    }

    public List<Dot> getDots() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dot> cq = cb.createQuery(Dot.class);
        Root<Dot> root = cq.from(Dot.class);
        CriteriaQuery<Dot> allDots = cq.select(root);
        TypedQuery<Dot> dotTypedQuery = entityManager.createQuery(allDots);
        return dotTypedQuery.getResultList();
    }


    public void addDot(Dot newDot) {
        entityManager.getTransaction().begin();
        entityManager.persist(newDot);
        entityManager.getTransaction().commit();
    }

    public Dot getDotById(int id) {
        return entityManager.find(Dot.class, id);
    }

    public void addDots(List<Dot> dotList) {
        entityManager.getTransaction().begin();
        for (Dot dot:dotList) {
            entityManager.persist(dot);
        }
        entityManager.getTransaction().commit();
    }

    public void deleteDotById(int id) {
        entityManager.getTransaction().begin();
        Dot find = entityManager.find(Dot.class, id);
        entityManager.remove(find);
        entityManager.getTransaction().commit();
    }

    public void save5Dots(List<Dot> dots) {
        if(dots.size() > 5) {
            dots = dots.subList(0,5);
        }
        addDots(dots);
    }

    public boolean isHit(double x, double y, double r) {
        return (x <= 0 && y >= 0 && pow(x, 2) + pow(y, 2) < pow(r, 2))
                || (x >= 0 && y >= 0 && y <= r && x <= r / 2)
                || (x <= 0 && y <= 0 && y >= -x - r);
    }
    private String getProperty(String name) {
        return properties.get(name).toString();
    }
}