import { useEffect, useState } from "react";
import { getProducts } from "../services/ProductService";
import { Product } from "../types";
import { useAuth } from "../services/AuthContext";
import { useNavigate } from "react-router-dom";
import { addToCart } from "../services/CartService";

interface ProductListProps {
  limit?: number;
  title: string;
}

const ProductListPage = ({ limit, title }: ProductListProps) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { user, isAuthenticated } = useAuth();
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

  const handleAddToCart = async (productId: number) => {
    if (isAuthenticated && user) {
      try {
        await addToCart(productId, user.id, 1);
        alert("Product added to cart successfully!");
      } catch (error) {
        console.error("Failed to add product to cart:", error);
        alert("Failed to add product to cart. Please try again.");
      }
    } else {
      alert("You must be logged in to add items to the cart.");
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
                  onClick={() => handleAddToCart(product.id)}
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
