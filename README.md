# e-commerce-with-Frontend

## Deployment Guide

### Backend Deployment (Railway)

1. **Connect to Railway**:
   - Go to [Railway.app](https://railway.app) and sign up/login
   - Connect your GitHub repository

2. **Add Database**:
   - In Railway dashboard, add a MySQL database
   - Copy the database credentials

3. **Deploy Backend**:
   - Railway will automatically detect and build your Spring Boot app
   - The `railway.json` and `Procfile` are configured for this

4. **Environment Variables** (Railway will set these automatically):
   - `DATABASE_URL`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `PORT`

### Frontend Deployment (Vercel)

1. **Connect to Vercel**:
   - Go to [Vercel.com](https://vercel.com) and sign up/login
   - Connect your GitHub repository
   - Select the `frontend` folder as root directory

2. **Configure Environment Variables**:
   - Add `VITE_API_URL` with your Railway backend URL (e.g., `https://your-app-name.railway.app/api`)

3. **Deploy**:
   - Vercel will automatically build and deploy your React app

### Local Development

1. **Backend**:
   ```bash
   mvn spring-boot:run
   ```

2. **Frontend**:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

### Technologies Used

- Backend: Spring Boot, Spring Data JPA, MySQL
- Frontend: React, TypeScript, Vite, Bootstrap
