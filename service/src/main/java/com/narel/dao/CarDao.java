package com.narel.dao;

import com.narel.dto.CarFilter;
import com.narel.entity.Car;
import com.narel.entity.Car_;
import com.narel.entity.Review;
import com.narel.entity.Review_;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.narel.entity.QCar.car;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDao {

    private static CarDao INSTANCE;

    public List<String> findCarModelByFilter(Session session, CarFilter filter) {
        QPredicate builder = QPredicate.builder();
        builder.add(filter.getBrand(), car.brand::eq);
        builder.add(filter.getYear(), car.year::eq);
        builder.add(filter.getFuelType(), car.fuelType::eq);
        builder.add(filter.getEngineCapacity(), car.engineCapacity::eq);
        var predicate = builder
                .buildAnd();

        return new JPAQuery<>(session)
                .select(car.model)
                .from(car)
                .where(predicate)
                .fetch();
    }

    public List<String> findReviewsByBrandAndModelCar(Session session, String brand, String model) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(String.class);

        var reviews = criteria.from(Review.class);
        var car = reviews.join(Review_.car);

        List<Predicate> predicates = new ArrayList<>();

        if (brand != null) {
            predicates.add(cb.equal(car.get(Car_.brand), brand));
        }
        if (model != null) {
            predicates.add(cb.equal(car.get(Car_.model), model));
        }

        criteria.select(reviews.get(Review_.reviewText)).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), session.getEntityGraph("ReviewsWithCar"))
                .getResultList();
    }

    public static synchronized CarDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarDao();
        }
        return INSTANCE;
    }
}
