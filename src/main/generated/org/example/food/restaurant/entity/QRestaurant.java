package org.example.food.restaurant.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = -869337255L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> lat = createNumber("lat", Double.class);

    public final NumberPath<Double> lng = createNumber("lng", Double.class);

    public final StringPath name = createString("name");

    public final ListPath<org.example.food.video.entity.Video, org.example.food.video.entity.QVideo> videos = this.<org.example.food.video.entity.Video, org.example.food.video.entity.QVideo>createList("videos", org.example.food.video.entity.Video.class, org.example.food.video.entity.QVideo.class, PathInits.DIRECT2);

    public QRestaurant(String variable) {
        super(Restaurant.class, forVariable(variable));
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurant(PathMetadata metadata) {
        super(Restaurant.class, metadata);
    }

}

