package main;

import javax.swing.JFrame;

import geo.Matrice;
import geo.Vecteur;
import geo.Vecteur2D;
import ui.MainWindow;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Main {

	public static void main(String s[]) {
		//Tests de vecteurs
		/*double coords1 [] = {1,2};
		Vecteur vecTest1 = new Vecteur(coords1);

		double coords2 [] = {3,4};
		Vecteur vecTest2 = new Vecteur(coords2);

		System.out.print("Norme du Vecteur 1 : ");
		System.out.println(vecTest1.norme());
		System.out.print("Norme du Vecteur 2 : ");
		System.out.println(vecTest2.norme());

		System.out.print("produit scalaire de vec1 et vec2 : ");
		System.out.print(vecTest1.produitScalaire(vecTest2));
		System.out.print(" & ");
		System.out.println(vecTest2.produitScalaire(vecTest1));

		System.out.print("cosinus de vec1 et vec2 : ");
		System.out.print(vecTest1.cosinus(vecTest2));
		System.out.print(" & ");
		System.out.println(vecTest2.cosinus(vecTest1));

		System.out.println("------------------------------------");

		*/
		//Test des vecteurs2
		/*
		Vecteur2D vec2dTest1 = new Vecteur2D(1,2);
		Vecteur2D vec2dTest2 = new Vecteur2D(4, 3);

		System.out.print("angle de vec2d1 et vec2d2 (à i) : ");
		System.out.print(vec2dTest1.angle());
		System.out.print(" & ");
		System.out.println(vec2dTest2.angle());

		System.out.println("------------------------------------");

		System.out.print("cosinus de vec2d1 et vec2d2 (à i) : ");
		System.out.print(vec2dTest1.cosinus());
		System.out.print(" & ");
		System.out.println(vec2dTest2.cosinus());

		System.out.print("sinus de vec2d1 et vec2d2 (à i) : ");
		System.out.print(vec2dTest1.sinus());
		System.out.print(" & ");
		System.out.println(vec2dTest2.sinus());

		System.out.print("tangente de vec2d1 et vec2d2 (à i) : ");
		System.out.print(vec2dTest1.tangente());
		System.out.print(" & ");
		System.out.println(vec2dTest2.tangente());

		System.out.println("------------------------------------");

		System.out.print("cosinus de vec2d1 et vec2d2 : ");
		System.out.print(vec2dTest1.cosinus(vec2dTest2));
		System.out.print(" & ");
		System.out.println(vec2dTest2.cosinus(vec2dTest1));

		System.out.print("sinus de vec2d1 et vec2d2 : ");
		System.out.print(vec2dTest1.sinus(vec2dTest2));
		System.out.print(" & ");
		System.out.println(vec2dTest2.sinus(vec2dTest1));

		System.out.println("------------------------------------");
		 */
		//Test des matrices
		double[][] coefs1 = {{1}};
		double[][] coefs2 = {{1,2},
							 {3,4}};
		double[][] coefs3 = {{8,7,3},
							 {6,5,6},
							 {0,0,9}};
		double[][] coefs5 = {{1,0,3,8,2},
							 {6,7,9,0,1},
				   			 {1,3,8,5,6},
							 {7,8,1,0,2},
							 {0,3,2,5,6}};
		Matrice mat1 = new Matrice(coefs1);
		Matrice mat2 = new Matrice(coefs2);
		Matrice mat3 = new Matrice(coefs3);
		Matrice mat5 = new Matrice(coefs5);

		System.out.println(mat1.getDeterminant());
		System.out.println(mat2.getDeterminant());
		System.out.println(mat3.getDeterminant());
		System.out.println(mat5.getDeterminant());

		//Programme Swing
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int x = 10, y = 10, w = 800, h = 600;
				MainWindow frame = new MainWindow(true, "Training Samples",x,y,w,h);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}});
	}
}
