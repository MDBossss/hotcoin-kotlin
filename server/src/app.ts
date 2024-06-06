import { config } from "dotenv";
import http from "http";
import express, { NextFunction, Request, Response } from "express";
import cors from "cors";
import loginRoutes from "./routes/loginRoutes";
import articleRoutes from "./routes/articleRoutes";

config();

const app = express();
const httpServer = http.createServer(app);

//Middleware
app.use(cors());

//Routes
app.use("/api/login", loginRoutes);
app.use("/api/articles", articleRoutes);

//Error handling middleware
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  console.error(err);
  res.status(500).json({ error: "Internal Server Error" });
});

//Start the server
const PORT = process.env.PORT || 3000;
httpServer.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
