export interface Product {
  id: number;
  name: string;
  price: number;
  stock: number;
}

export interface User {
  userId: number;
  username: string;
  email: string;
  // Buraya rol veya token gibi diğer kullanıcı özelliklerini ekleyebilirsiniz
}
