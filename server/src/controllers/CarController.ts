import { Request, Response } from "express";
import { prisma } from "../utils/prisma";
import { Car } from "@prisma/client";

class CarController {
  async getAllCars(req: Request, res: Response) {
    console.log("[INFO] Called: getAllCars()");
    try {
      const cars = await prisma.car.findMany();

      res.status(200).json(cars);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async getLatestCar(req: Request, res: Response) {
    console.log("[INFO] Called: getLatestCar()");
    try {
      const cars = await prisma.car.findMany({
        orderBy: {
          updatedAt: "desc",
        },
        take: 1,
      });

      res.status(200).json(cars[0]);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async getCarById(req: Request, res: Response) {
    try {
      const { id } = req.params;

      const car = await prisma.car.findUnique({
        where: { id },
      });

      if (!car) {
        return res.status(404).json({ error: "Car not found" });
      }
      res.status(200).json(car);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async createCar(req: Request, res: Response) {
    console.log("[INFO] Called: createCar()");

    try {
      const car: Car = req.body;

      const newCar = await prisma.car.create({
        data: {
          brand: car.brand,
          model: car.model,
          color: car.color,
          imageUrl: car.imageUrl,
        },
      });
      res.status(201).json(newCar);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async updateCar(req: Request, res: Response) {
    try {
      const { id } = req.params;
      const car: Car = req.body;
      const updatedCar = await prisma.car.update({
        where: { id },
        data: {
          brand: car.brand,
          model: car.model,
          color: car.color,
          imageUrl: car.imageUrl,
        },
      });
      res.status(200).json(updatedCar);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }

  async deleteCar(req: Request, res: Response) {
    try {
      const { id } = req.params;
      await prisma.car.delete({ where: { id } });
      res.status(204).json({ message: "Car deleted successfully" });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
}

export default CarController;
