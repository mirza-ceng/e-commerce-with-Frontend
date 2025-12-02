import { useEffect, useState } from "react";
import { getProducts } from "../services/ProductService";
import { Product } from "../types";
import { useAuth } from "../services/AuthContext";
import { useNavigate } from "react-router-dom";

interface ProductListProps {
  limit?: number;
  title: string;
}

const ProductListPage = ({ limit, title }: ProductListProps) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await getProducts();
        setProducts(data);
      } catch (err) {
        setError("Failed to fetch products");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const handleAddToCart = (product: Product) => {
    if (isAuthenticated) {
      // Logic to add product to cart
      console.log(`${product.name} added to cart`);
    } else {
      navigate("/login");
    }
  };

  if (loading) {
    return <div className="container mt-4">Loading...</div>;
  }

  if (error) {
    return <div className="container mt-4 text-danger">{error}</div>;
  }

  const displayedProducts = limit ? products.slice(0, limit) : products;

  return (
    <div className="container mt-4">
      <h2>{title}</h2>
      <div className="row">
        {displayedProducts.map((product) => (
          <div className="col-md-4 mb-4" key={product.id}>
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{product.name}</h5>
                <p className="card-text">${product.price.toFixed(2)}</p>
                <p className="card-text">
                  <small>Stock: {product.stock}</small>
                </p>
                <button
                  className="btn btn-primary"
                  onClick={() => handleAddToCart(product)}
                >
                  Add to Cart
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductListPage;
