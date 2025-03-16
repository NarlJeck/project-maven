package com.narel.repository;

import com.narel.dto.QPredicate;
import com.narel.dto.CarFilter;
import com.narel.dto.CarReviewsFilter;
import com.narel.entity.Car;
import com.narel.entity.Car_;
import com.narel.entity.Review;
import com.narel.entity.Review_;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.ArrayList;
import java.util.List;

import static com.narel.entity.Car_.model;
import static com.narel.entity.QCar.car;

public class CarRepository extends BaseRepository<Integer, Car>{

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }
    public List<String> findCarModelByFilter(Session session, CarFilter filter) {
        QPredicate builder = QPredicate.builder();
        builder
                .add(filter.getBrand(), car.brand::eq)
                .add(filter.getYear(), car.year::eq)
                .add(filter.getFuelType(), car.fuelType::eq)
                .add(filter.getEngineCapacity(), car.engineCapacity::eq);
        var predicate = builder
                .buildAnd();

        return new JPAQuery<>(session)
                .select(car.model)
                .from(car)
                .where(predicate)
                .fetch();
    }

    public List<String> findReviewsByBrandAndModelCar(Session session, CarReviewsFilter filter) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(String.class);
        var reviews = criteria.from(Review.class);
        var car = reviews.join(Review_.car);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getBrand() != null) {
            predicates.add(cb.equal(car.get(Car_.brand), filter.getBrand()));
        }
        if (filter.getModel() != null) {
            predicates.add(cb.equal(car.get(model), filter.getModel()));
        }
        criteria.select(reviews.get(Review_.reviewText)).where(
                predicates.toArray(Predicate[]::new)
        );
        return session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), session.getEntityGraph("ReviewsWithCar"))
                .getResultList();
    }
}
