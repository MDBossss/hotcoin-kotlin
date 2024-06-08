import express, { Router } from "express";
import AuthController from "../controllers/AuthController";

const router: Router = express.Router();
const authController = new AuthController();

router.use(express.json());

router.post("/login", authController.login);
router.post("/register", authController.register);

export default router;
