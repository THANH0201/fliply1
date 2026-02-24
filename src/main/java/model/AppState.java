package model;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entity.ClassModel;
import model.entity.Flashcard;
import model.entity.FlashcardSet;
import model.entity.Quiz;
import model.entity.User;

import java.util.HashMap;
import java.util.Map;

public final class AppState {

    // ---------- USER SESSION ----------
    public static final ObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    public static final ObjectProperty<Role> role = new SimpleObjectProperty<>(Role.STUDENT);

    // ---------- Role ----------
    public enum Role { STUDENT, TEACHER }
    public static AppState.Role getRole() {
        return role.get();
    }
    public static void setRole(AppState.Role r) {
        role.set(r);
    }
    public static boolean isTeacher() {
        return role.get() == Role.TEACHER;
    }

    // ---------- NAVIGATION ----------
    public static final ObjectProperty<Screen> currentScreen = new SimpleObjectProperty<>(Screen.WELCOME);
    public static final ObjectProperty<NavItem> activeNav = new SimpleObjectProperty<>(Screen.WELCOME.nav);
    public static final ObjectProperty<NavItem> navOverride = new SimpleObjectProperty<>(null);

    public enum NavItem { HOME, CLASSES, FLASHCARDS, QUIZZES, ACCOUNT, NONE }

    public enum Screen {
        WELCOME("/screens/welcome.fxml", NavItem.NONE),
        LOGIN("/screens/login.fxml", NavItem.NONE),
        REGISTER("/screens/register.fxml", NavItem.NONE),

        HOME("/screens/home.fxml", NavItem.HOME),

        CLASSES("/screens/classes.fxml", NavItem.CLASSES),
        CLASS_DETAIL("/screens/class_detail.fxml", NavItem.CLASSES),
        FLASHCARD_SET("/screens/flashcard_set.fxml", NavItem.CLASSES),
        FLASHCARD_DETAIL("/screens/flashcard_detail.fxml", NavItem.CLASSES),

        FLASHCARDS("/screens/flashcards.fxml", NavItem.FLASHCARDS),
        FLASHCARD_FORM("/screens/flashcard_form.fxml", NavItem.FLASHCARDS),

        QUIZZES("/screens/quizzes.fxml", NavItem.QUIZZES),
        QUIZ_FORM("/screens/quiz_form.fxml", NavItem.QUIZZES),
        QUIZ_DETAIL("/screens/quiz_detail.fxml", NavItem.QUIZZES),
        QUIZ_RESULT("/screens/quiz_result.fxml", NavItem.QUIZZES),

        TEACHER_ADD_CLASS("/screens/teacher_add_class.fxml", NavItem.CLASSES),
        TEACHER_ADD_SET("/screens/teacher_add_set.fxml", NavItem.CLASSES),
        TEACHER_STUDENT_DETAIL("/screens/teacher_student_detail.fxml", NavItem.CLASSES),
        TEACHER_CLASS_DETAIL("/screens/teacher_class_detail.fxml", NavItem.CLASSES),
        TEACHER_FLASHCARD_SET_DETAIL("/screens/teacher_flashcard_set_detail.fxml", NavItem.CLASSES),

        ACCOUNT("/screens/account.fxml", NavItem.ACCOUNT),
        ACCOUNT_EDIT("/screens/account_edit.fxml", NavItem.ACCOUNT),
        ACCOUNT_PASSWORD("/screens/account_password.fxml", NavItem.ACCOUNT),
        ACCOUNT_HELP("/screens/account_help.fxml", NavItem.ACCOUNT),
        ACCOUNT_ABOUT("/screens/account_about.fxml", NavItem.ACCOUNT);

        public final String fxml;
        public final NavItem nav;

        Screen(String fxml, NavItem nav) {
            this.fxml = fxml;
            this.nav = nav;
        }
    }


    // ---------- CLASSES STATE ----------
    public static final ObjectProperty<ClassModel> selectedClass = new SimpleObjectProperty<>(null);
    public static final ObjectProperty<User> selectedStudent = new SimpleObjectProperty<>(null);

    // ---------- FLASHCARD FORM STATE ----------
    public static final ObjectProperty<FlashcardSet> selectedFlashcardSet = new SimpleObjectProperty<>(null);
    public static final ObjectProperty<FlashcardSet> selectedSet = new SimpleObjectProperty<>(null);
    public static final ObjectProperty<FormMode> flashcardFormMode = new SimpleObjectProperty<>(FormMode.ADD);
    public static final IntegerProperty editingIndex = new SimpleIntegerProperty(-1);
    public static final ObservableList<Flashcard> currentDetailList = FXCollections.observableArrayList();
    public static final ObjectProperty<Flashcard> currentFlashcard = new SimpleObjectProperty<>();
    public static final IntegerProperty currentDetailIndex = new SimpleIntegerProperty(0);
    public static final StringProperty selectedFlashcardSetName = new SimpleStringProperty("");
    public enum FormMode { ADD, EDIT }
    public static final ObservableList<Flashcard> myFlashcards = FXCollections.observableArrayList();
    public static final StringProperty selectedDefinition = new SimpleStringProperty("");
    public static final StringProperty selectedTerm = new SimpleStringProperty("");

    // ---------- QUIZZES FORM STATE ----------
    public static final ObservableList<Quiz> myQuizzes = FXCollections.observableArrayList();
    public static final ObjectProperty<Quiz> selectedQuiz = new SimpleObjectProperty<>(null);
    public static final IntegerProperty quizQuestionIndex = new SimpleIntegerProperty(0);
    public static final IntegerProperty quizPoints = new SimpleIntegerProperty(0);
    public static final Map<Integer, String> quizAnswers = new HashMap<>();
    public static final Map<Integer, Boolean> quizCorrectMap = new HashMap<>();
    public static final ObservableList<Quiz> quizList = FXCollections.observableArrayList();

    // ---------- UI STATE ----------
    public static final StringProperty detailHeaderTitle = new SimpleStringProperty("");
    public static final StringProperty detailHeaderSubtitle = new SimpleStringProperty("");
    public static final BooleanProperty isFromFlashcardSet = new SimpleBooleanProperty(false);

    private AppState() {}
}
