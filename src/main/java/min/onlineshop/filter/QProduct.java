package min.onlineshop.filter;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.*;
import min.onlineshop.domains.Product;
import min.onlineshop.domains.ProductCategory;

import java.util.Date;

public class QProduct extends EntityPathBase<Product> {
  public static final QProduct productEntity = new QProduct("product");

  public final NumberPath<Long> id;
  public final StringPath title;
  public final EnumPath<ProductCategory> category;
  public final DateTimePath<Date> createdAt;
  public final StringPath createdBy;
  public final DateTimePath<Date> updatedAt;
  public final StringPath updatedBy;

  public QProduct(String variable) {
    super(Product.class, PathMetadataFactory.forVariable(variable));

    this.id = this.createNumber("id", Long.class);
    this.title = this.createString("title");
    this.category = this.createEnum("category", ProductCategory.class);
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);

  }

  public QProduct(PathMetadata metadata) {
    super(Product.class, metadata);
    this.id = this.createNumber("id", Long.class);
    this.title = this.createString("title");
    this.category = this.createEnum("category", ProductCategory.class);
    this.createdBy = this.createString("createdBy");
    this.updatedBy = this.createString("updatedBy");
    this.createdAt = this.createDateTime("createdAt", Date.class);
    this.updatedAt = this.createDateTime("updatedAt", Date.class);
  }
}
