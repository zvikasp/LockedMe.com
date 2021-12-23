package com.locker.project;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class LockerFiles {
	void listFiles(String dir) {
		String[] fileList = new File(dir).list();
		if (fileList.length == 0) {
			System.out.println("Root directory is empty.");
			return;
		}

		List<String> lst = Arrays.asList(fileList);
		Iterator<String> itr = lst.iterator();
		while (itr.hasNext()) {
			String string = (String) itr.next();
			System.out.println(string);
		}
	}

	void serachFiles(String inDir, String pattern) {
		File dir = new File(inDir);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.contains(pattern.toLowerCase());
			}
		};
		String[] children = dir.list(filter);
		if (children == null) {
			System.out.println("Either dir does not exist or is not a directory");
		} else {
			if (children.length == 0) {
				System.out.println("No file found with provided ");
				return;
			}
			for (int i = 0; i < children.length; ++i) {
				String filename = children[i];
				System.out.println(filename);
			}
		}
	}

	void addDirectory(String dirName) {
		File directory = new File(dirName);
		if (directory.exists())
			System.out.println("Root Directory already exists");
		else if (directory.mkdir())
			System.out.println("Root Directory Created");
		else
			System.out.println("Root Directory is not created");
	}

	void addFile(String dir, String fileName) {
		try {
			if (fileName.endsWith(".txt"))
				fileName = dir + "//" + fileName;
			else
				fileName = dir + "//" + fileName + ".txt";
			File file = new File(fileName);
			if (file.createNewFile())
				System.out.println("File is created!");
			else {
				System.out.println("File already exists");
			}

			FileWriter writer = new FileWriter(file);
			writer.write("Test data");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String DeleteUserFile(String dir, String fileName) {
		if (fileName.endsWith(".txt"))
			fileName = dir + "//" + fileName;
		else
			fileName = dir + "//" + fileName + ".txt";
		try {
			Files.deleteIfExists(Paths.get(fileName, new String[0]));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/ directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty");
		} catch (IOException e) {
			System.out.println("Invalid Permissions.");
		}
		System.out.println("Deletion successful");
		return "";
	}
}

public class Locker {
	public static void main(String[] args) {
		System.out.println("****************************************************************");
		System.out.println("\t\tWelcome to Lockers Pvt. Ltd.");
		System.out.println("****************************************************************");
		System.out.println("\t\tLockedMe.com - Developed by Vikas Panwar");
		System.out.println("****************************************************************");
		System.out.println("\tFull Stack Java Developer - Simplilearn");
		System.out.println("****************************************************************");
		try {
			int choice;
			String ROOTFOLDER = "Locker";
			System.out.println("****************************************************************");
			System.out.println("Root directory is [" + ROOTFOLDER + "]");
			System.out.println("****************************************************************");
			LockerFiles locker = new LockerFiles();
			locker.addDirectory(ROOTFOLDER);
			do {
				System.out.println("Enter your choice?");
				System.out.println("*************************************");
				System.out.println("1. Show all files in the directory");
				System.out.println("2. Business level operations");
				System.out.println("0. Close the application");

				Scanner scan = new Scanner(System.in);
				try {
					choice = scan.nextInt();
				} catch (Exception e) {
					choice = 9;
				}
				switch (choice) {
				case 1:
					locker.listFiles(ROOTFOLDER);
					break;
				case 2:
					int choice2;
					do {
						System.out.println("Choose required business operation?");
						System.out.println("*************************************");
						System.out.println("1. Add a file to the directory");
						System.out.println("2. Delete file from the directory");
						System.out.println("3. Search a file from the directory");
						System.out.println("0. Exit business operations");
						scan = new Scanner(System.in);
						try {
							choice2 = scan.nextInt();
						} catch (Exception e) {
							choice2 = 9;
						}

						String fileName = "";
						String pattern = "";
						switch (choice2) {
						case 1:
							System.out.println("Enter File to be added?");
							fileName = scan.next();
							locker.addFile(ROOTFOLDER, fileName);
							break;
						case 2:
							System.out.println("Enter File to be deleted?");
							fileName = scan.next();
							locker.DeleteUserFile(ROOTFOLDER, fileName);
							break;
						case 3:
							System.out.println("Enter Filename pattern to search?");
							pattern = scan.next();
							locker.serachFiles(ROOTFOLDER, pattern);
							break;
						case 0:
							System.out.println("Thanks you. Exiting File Operations!!");
							break;
						default:
							System.out.println("INVALID option.");
						}

						System.out.println();
					} while (choice2 != 0);
					break;
				case 0:
					System.out.println("Thanks you for using LockedMe.com!!");
					break;
				default:
					System.out.println("INVALID option.");
				}
			} while (choice != 0);
		} catch (Exception localException) {
			System.out.println("Some exception...");
		}
	}
}