# Real Estate Agent Application

## Overview

This application is designed for real estate agents to manage their profiles, properties, and meetings efficiently. Agents can create their profiles, add properties they have exclusivity on, and schedule and manage meetings with potential clients. The application uses Firebase Authentication and Firebase Realtime Database to store agent details, property information, and meeting schedules in real-time.

## Features

### User Authentication
- **Email and Phone Login**: Agents can log in using their email or phone number.
- **Profile Creation**: Agents can create and manage their profiles.

### Object Management
- **Agent Object**: Represents an agent with details like UID and list of Assets.
- **Asset Object**: Represents a property with details like city, street, street number, floor, sale/rent status, price, and owner details.
- **Meeting Object**: Represents a meeting with details like date, time, participants, and additional information.
- **MeetingList Object**: Represents a list of meetings associated with an asset.

### Property and Meeting Management
- **Add New Assets**: Agents can add properties they have exclusivity on using the Asset object.
- **Delete Assets**: Agents can delete properties from their list.
- **Meeting Scheduling**: Agents can schedule meetings for each property using the Meeting object.
- **Manage Meetings**: Agents can view and manage their scheduled meetings.
- **Delete Meetings**: Agents can delete meetings that have ended.
- **RecyclerView for Asset and Meeting Display**: All assets and meetings for each agent are displayed using RecyclerView for a user-friendly interface.
- **Unique Adapters for Asset and Meeting RecyclerViews**: Each RecyclerView for assets and meetings uses a unique adapter to display the corresponding list of items.
- **Fragment for Meeting List**: The meeting list is displayed using Fragments for organized and modular code structure.
- **CardView for Asset and Meeting Information**: Asset and meeting information is displayed using CardView within the RecyclerView's ViewHolder for a structured and visually appealing view.

## Technologies Used

- **Firebase Authentication**: For user authentication and profile management.
- **Firebase Realtime Database**: To store and retrieve agent profiles, property details, and meeting schedules in real-time.
- **RecyclerView**: To display all assets and meetings in a list format.
- **Fragments**: To display the meeting list in a structured and organized manner.
- **CardView**: To display detailed asset and meeting information within the RecyclerView's ViewHolder for a visually appealing interface.

## Setup

### Prerequisites

- Android Studio
- Firebase account

## Usage

1. **Login/Register**: Open the application and either log in with your email/phone or register to create a new account.
2. **Add New Assets**: Navigate to the 'Add Asset' section to add new properties using the Asset object.
3. **Manage Meetings**: Schedule, view, and delete meetings under the 'Meetings' section for each property using the Meeting object.
4. **View Assets and Meetings**: All assets and meetings for each agent are displayed using RecyclerView for easy navigation and management.
5. **Meeting List Fragment**: Navigate to the 'Meetings' section to view the meeting list displayed using Fragments for a structured and organized view.
6. **Asset Information CardView**: View detailed asset information within each RecyclerView item using CardView for a visually appealing interface.
7. **Meeting Information CardView**: View detailed meeting information within each RecyclerView item using CardView for a visually appealing interface.
