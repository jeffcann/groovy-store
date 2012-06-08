package au.com.dius.shop

class ProductTest extends GroovyTestCase
{
    void testCreatingProduct() {

        Product product = new Product("sku", "name", "\$123.45")

        assert product.sku == "sku"
        assert product.name == "name"
        assert product.price == 123.45
    }
}
