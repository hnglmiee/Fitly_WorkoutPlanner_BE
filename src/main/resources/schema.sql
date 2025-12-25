CREATE TABLE IF NOT EXISTS ExerciseCategories
(
    CategoryId   INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(100) NOT NULL,
    Description  VARCHAR(255) NULL,
    CONSTRAINT CategoryName UNIQUE (CategoryName)
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS Exercises
(
    ExerciseId  INT AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR(100) NOT NULL,
    Description TINYTEXT NULL,
    CategoryId  INT NULL,
    CONSTRAINT FK_Exercises_Category
        FOREIGN KEY (CategoryId) REFERENCES ExerciseCategories (CategoryId)
            ON DELETE SET NULL
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS InvalidatedToken
(
    id         VARCHAR(255) NOT NULL PRIMARY KEY,
    expiryTime DATETIME(6) NULL
);

CREATE TABLE IF NOT EXISTS Roles
(
    RoleId   INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL,
    CONSTRAINT RoleName UNIQUE (RoleName)
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS Users
(
    UserId       INT AUTO_INCREMENT PRIMARY KEY,
    FullName     VARCHAR(100) NOT NULL,
    Email        VARCHAR(255) NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    PhoneNumber  VARCHAR(20) NULL,
    CreatedAt    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    UpdatedAt    TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    RoleId       INT NULL,
    Birthday     DATE NULL,
    Gender       VARCHAR(10) NULL,
    CONSTRAINT Email UNIQUE (Email),
    CONSTRAINT FK_Users_Role
        FOREIGN KEY (RoleId) REFERENCES Roles (RoleId)
            ON DELETE SET NULL
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS UserGoals
(
    GoalId                       INT AUTO_INCREMENT PRIMARY KEY,
    EndDate                      DATE NULL,
    GoalName                     VARCHAR(100) NOT NULL,
    Notes                        VARCHAR(255) NULL,
    StartDate                    DATE NULL,
    Status                       VARCHAR(20) NULL,
    TargetBodyFatPercentage      DECIMAL(5, 2) NULL,
    TargetCaloriesPerDay         INT NULL,
    TargetMuscleMass             DECIMAL(6, 2) NULL,
    TargetWeight                 DECIMAL(6, 2) NULL,
    TargetWorkoutSessionsPerWeek INT NULL,
    UserId                       INT NOT NULL,
    CONSTRAINT FKn4wa7evenba80friak1uuan9o
        FOREIGN KEY (UserId) REFERENCES Users (UserId)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS UserInbody
(
    InbodyId          INT AUTO_INCREMENT PRIMARY KEY,
    UserId            INT NOT NULL,
    MeasuredAt        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    Height            DECIMAL(5, 2) NULL,
    Weight            DECIMAL(5, 2) NULL,
    BodyFatPercentage DECIMAL(5, 2) NULL,
    MuscleMass        DECIMAL(6, 2) NULL,
    Notes             VARCHAR(255) NULL,
    Age               INT NULL,
    CONSTRAINT FK_UserInbody_User
        FOREIGN KEY (UserId) REFERENCES Users (UserId)
            ON DELETE CASCADE
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS WorkoutPlans
(
    PlanId    INT AUTO_INCREMENT PRIMARY KEY,
    UserId    INT NOT NULL,
    Title     VARCHAR(150) NOT NULL,
    Notes     TINYTEXT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    UpdatedAt TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT FK_WorkoutPlans_User
        FOREIGN KEY (UserId) REFERENCES Users (UserId)
            ON DELETE CASCADE
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS WorkoutExercises
(
    WorkoutExerciseId INT AUTO_INCREMENT PRIMARY KEY,
    PlanId            INT NOT NULL,
    ExerciseId        INT NOT NULL,
    Sets              INT NOT NULL,
    Reps              INT NOT NULL,
    Weight            DECIMAL(6, 2) NULL,
    Comments          VARCHAR(255) NULL,
    CONSTRAINT FK_WorkoutExercises_Exercise
        FOREIGN KEY (ExerciseId) REFERENCES Exercises (ExerciseId)
            ON DELETE CASCADE,
    CONSTRAINT FK_WorkoutExercises_Plan
        FOREIGN KEY (PlanId) REFERENCES WorkoutPlans (PlanId)
            ON DELETE CASCADE,
    CHECK (Sets > 0),
    CHECK (Reps > 0)
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS WorkoutSchedules
(
    ScheduleId    INT AUTO_INCREMENT PRIMARY KEY,
    PlanId        INT NOT NULL,
    ScheduledDate DATE NOT NULL,
    Status        VARCHAR(20) DEFAULT 'Pending' NULL,
    ReminderSent  TINYINT(1) DEFAULT 0 NULL,
    ScheduledTime TIME NULL,
    CONSTRAINT FK_WorkoutSchedules_Plan
        FOREIGN KEY (PlanId) REFERENCES WorkoutPlans (PlanId)
            ON DELETE CASCADE,
    CHECK (Status IN ('Pending', 'Completed', 'Skipped'))
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS SmsNotifications
(
    SmsId            INT AUTO_INCREMENT PRIMARY KEY,
    UserId           INT NULL,
    ScheduleId       INT NULL,
    Message          TINYTEXT NOT NULL,
    SentAt           TIMESTAMP NULL,
    Status           VARCHAR(20) DEFAULT 'queued' NULL,
    ProviderResponse TINYTEXT NULL,
    CONSTRAINT FK_SmsNotifications_Schedule
        FOREIGN KEY (ScheduleId) REFERENCES WorkoutSchedules (ScheduleId)
            ON DELETE SET NULL,
    CONSTRAINT FK_SmsNotifications_User
        FOREIGN KEY (UserId) REFERENCES Users (UserId)
            ON DELETE CASCADE,
    CHECK (Status IN ('queued', 'sent', 'failed'))
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS WorkoutExerciseProgress
(
    Id                INT AUTO_INCREMENT PRIMARY KEY,
    ScheduleId        INT NOT NULL,
    WorkoutExerciseId INT NOT NULL,
    IsCompleted       TINYINT(1) DEFAULT 0 NULL,
    CompletedAt       TIMESTAMP NULL,
    CONSTRAINT UX_Progress_Schedule_Exercise UNIQUE (ScheduleId, WorkoutExerciseId),
    CONSTRAINT FK_Progress_Exercise
        FOREIGN KEY (WorkoutExerciseId) REFERENCES WorkoutExercises (WorkoutExerciseId)
            ON DELETE CASCADE,
    CONSTRAINT FK_Progress_Schedule
        FOREIGN KEY (ScheduleId) REFERENCES WorkoutSchedules (ScheduleId)
            ON DELETE CASCADE
) CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS WorkoutLogs
(
    LogId        INT AUTO_INCREMENT PRIMARY KEY,
    ScheduleId   INT NOT NULL,
    ExerciseId   INT NOT NULL,
    ActualSets   INT NULL,
    ActualReps   INT NULL,
    ActualWeight DECIMAL(6, 2) NULL,
    Notes        VARCHAR(255) NULL,
    LoggedAt     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    CONSTRAINT FK_WorkoutLogs_Exercise
        FOREIGN KEY (ExerciseId) REFERENCES Exercises (ExerciseId)
            ON DELETE CASCADE,
    CONSTRAINT FK_WorkoutLogs_Schedule
        FOREIGN KEY (ScheduleId) REFERENCES WorkoutSchedules (ScheduleId)
            ON DELETE CASCADE
) CHARSET = utf8mb4;