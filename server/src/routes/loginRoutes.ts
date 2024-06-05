import express, { Router } from "express";
import LoginController from "../controllers/LoginController";

const router: Router = express.Router();
const loginController = new LoginController();

router.use(express.json());

router.post("/", loginController.login);

export default router;
