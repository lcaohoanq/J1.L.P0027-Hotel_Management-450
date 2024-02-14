package models;

public interface FileService {
    void saveToFile(String url);
    void loadDataFromFile(String url);
}
