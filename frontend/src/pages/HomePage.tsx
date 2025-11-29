import ProductListPage from "./ProductListPage";

const HomePage = () => {
  return (
    <div>
      <div className="container mt-4">
        <div className="p-5 mb-4 bg-light rounded-3">
          <div className="container-fluid py-5">
            <h1 className="display-5 fw-bold">
              Welcome to our E-Commerce Store
            </h1>
            <p className="col-md-8 fs-4">
              Browse our collection of amazing products.
            </p>
          </div>
        </div>
      </div>
      <ProductListPage title="Featured Products" limit={3} />
    </div>
  );
};

export default HomePage;