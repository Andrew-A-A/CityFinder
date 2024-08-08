# City Finder Android App

This Android application allows users to efficiently search through a large list of cities and view their locations on a map. It's designed to handle prefix-based searches with optimized performance.

## Features

* **Fast Prefix Search:** The app utilizes a Trie (Prefix Tree) data structure to enable rapid filtering of cities based on user input.
* **Case-Insensitive Search:** Search functionality is case-insensitive, providing a user-friendly experience.
* **Scrollable List:** Search results are displayed in an alphabetically sorted, scrollable list.
* **Map Integration:** Tapping on a city in the list opens its location on Google Maps.
* **Responsive UI:** The user interface remains responsive even during active typing in the search field.

## Technical Details

* **Data Loading:** The app loads a JSON file containing city data using the GSON library.
* **Data Structure:** The city data is stored in a Trie for efficient prefix searching.
* **Search Algorithm:** The search algorithm leverages the Trie's structure to achieve better than linear time complexity.

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

## Usage

1. Launch the app.
2. Start typing in the search field to filter cities.
3. Tap on a city in the list to view its location on Google Maps.

## Additional Notes

* The app is designed to handle screen rotations gracefully.
* The initial loading time of the app might be slightly longer due to data preprocessing into the Trie structure, but subsequent searches are very fast.

## Screenshot
<img src="https://github.com/user-attachments/assets/9039d31e-6c34-4d6b-a8da-4fe8b2f5d765" width="220"/>
<img src="https://github.com/user-attachments/assets/1878a2cd-0fa4-4361-80e9-9fb79194dbee" width="220"/>
<img src="https://github.com/user-attachments/assets/d852d9f3-485f-4f4b-a6c8-0ef2d36cbd80" width="220"/>




