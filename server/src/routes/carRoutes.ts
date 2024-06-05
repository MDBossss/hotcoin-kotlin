import express, { Router } from "express";
import CarController from "../controllers/CarController";

const router: Router = express.Router();
const carController = new CarController();

router.use(express.json());

// Routes
router.get("/", carController.getAllCars);
router.get("/latest", carController.getLatestCar);
router.get("/:id", carController.getCarById);
router.post("/", carController.createCar);
router.put("/:id", carController.updateCar);
router.delete("/:id", carController.deleteCar);

export default router;
