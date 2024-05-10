
# 📚 Data Structures and Algorithms Final Project 📚

This README provides instructions on how to set up and run the Data Structures and Algorithms final project. Follow these steps carefully to ensure the application runs smoothly.

## Prerequisites 🛠️

Before you begin, ensure you have the following installed:
- ☕ Java (JDK 11 or later)
- 🟩 Node.js and npm

## Installation 📦

Follow these steps to get your development environment set up:

### Step 1: Download the Data File 📄

Download the `places.txt` file. This file contains the data needed for the application to run.

### Step 2: Prepare Data Folder 📂

Copy the `places.txt` file into the `data` folder in your project directory.

## Backend Setup 🌐

### Step 3: Run the SpringBoot Backend

1. Navigate to the `src/main/java/com/example/testapi` directory.
2. Find the `APIApplication.java` class and run it. This action starts the SpringBoot backend.

## Frontend Setup 🖥️

### Step 4: Set Up the Frontend Environment

1. Open the terminal.
2. Change directory to the frontend source folder:
   ```bash
   cd fe/src
   ```
   Alternatively, right-click on the folder and select "Open in Integrated Terminal".

### Step 5: Install Dependencies

Run the following command to install all the necessary modules:
```bash
npm i
```
Wait for the installation to complete before moving to the next step.

### Step 6: Start the React Application 🚀

Execute the following command to start the React app:
```bash
npm start
```

### Step 7: Application Loading ⏳

Please be patient as the React application might take some time to load.

### Step 8: Verify Startup 🖥️

Once the application starts up in your browser, the setup is complete.

## Usage 🎯

Now that the application is running, you can start using it. Enjoy exploring the functionalities integrated into our Data Structures and Algorithms project.

### Searching for nearest POIs 
#### Step 1: Set up the target and bounding rectangle
In the top left corner of the React app, you will see a gear icon
![image](https://github.com/vinhnt2914/DSA_FinalProject/assets/113036284/e30173f2-db9f-4986-8eea-bbb5144d5e20)

clicking on it will pop up a form. Inside the form, please enter the coordinates, separated by commas, like this:
```bash
10000,10000
```
Then enter your preferred bounding size. You will be automatically repositioned to the target's location that you've just entered. You can zoom out to see the bounding rectangle better.

#### Step 2: Pick a service type
In the combo box to the right of the gear icon, select any type of service you want. Then click on the search icon to begin your searching

#### Step 3: Enjoy the result
The display is zoomed in by default so please zoom out to see all of the POIs together with the bounding rectangle

### Add a POI
#### Step 1: Enter the POI coordinates and services
In the bottom right corner of the React app, there are 2 buttons. Click on the "Add a place" option. You will see a pop-up form. Enter the POI's x-axis, y-axis, and list of services in the appropriate input field.

#### Step 2: Submit the form
Once you've entered information for the POI, click on the "Add a place" button inside the form. If done correctly, you will see a green text line on the bottom of the input form, informing that you have add the place successfully

### Searching for a specific POI
#### Step 1: Enter the POI coordinates
To the right of the "Add a place" button is the "Edit/Manage a place" option. Clicking it will pop up a form, you can enter the X and Y coordinates of any POI and search for it. If found, it will display the search result. Otherwise, you will see red text information that the POI you want to search for does not exist

### Edit a POI's services
#### Step 1: Select new services
This is the follow-up of the "searching for a specific POI" feature. Once you have seen your POI under the "Search Result", select the "Edit" option, this will extend the form with a combo box for you to select your new list of services. Note that, this will overwrite the current POI's list of services, not adding or removing from it.

#### Step 2: Check your result
After you have entered the new services and submit. Search for the POI again, you should be able to see the POI's list of services has changed.

### Remove a POI
#### Step 1: Delete the POI
This is the follow-up of the "searching for a specific POI" feature. Once you have seen your POI under the "Search Result", select the "Delete" option.

#### Step 2: Check your result
Search for the POI again, you should be able to see a red text informing you that the POI you entered does not exists

## Authors 👥

- 📘 Pham Thanh Mai
- 📗 Nguyen The Vinh
- 📙 Tran Tuan Minh
- 📔 Nguyen Duy Anh

## Support 🔧

For any technical issues or questions, please refer to our support guidelines or contact us directly through our project's communication channels.

Thank you for setting up our Data Structures and Algorithms final project!
