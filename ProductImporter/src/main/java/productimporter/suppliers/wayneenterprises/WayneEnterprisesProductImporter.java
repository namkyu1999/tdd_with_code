package productimporter.suppliers.wayneenterprises;

import java.util.ArrayList;

import productimporter.Product;
import productimporter.ProductImporter;

public final class WayneEnterprisesProductImporter implements ProductImporter {

    final WayneEnterprisesProductSource dataSource;

    public WayneEnterprisesProductImporter(WayneEnterprisesProductSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Product> fetchProducts() {

        Iterable<WayneEnterprisesProduct> products = dataSource.fetchProducts();
        
        products.iterator().
        return products;
    }

}
