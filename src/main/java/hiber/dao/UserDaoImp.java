package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Query findUserByCar = sessionFactory.getCurrentSession().createQuery(
                "FROM Car WHERE model = :model AND series = :series")
                .setParameter("model", model)
                .setParameter("series", series);
        List<Car> findCarList = findUserByCar.getResultList();
        if (!findCarList.isEmpty()){
            Car finded = findCarList.get(0);
            List<User> userList = listUsers();
            return (User) userList.stream()
                    .filter(user -> user.getCar().equals(finded))
                    .findAny()
                    .orElse(null);
        }
        return null;
    }

}
