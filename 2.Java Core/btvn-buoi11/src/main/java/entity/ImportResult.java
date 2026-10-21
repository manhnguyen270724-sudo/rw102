package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Model chứa kết quả thực hiện import từ file CSV.
 */
public class ImportResult {
    private int totalRows;
    private int successCount;
    private int errorCount;
    private String errorFilePath;
    private List<String> errorMessages = new ArrayList<>();

    public ImportResult() {
    }

    public ImportResult(int totalRows, int successCount, int errorCount, String errorFilePath, List<String> errorMessages) {
        this.totalRows = totalRows;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.errorFilePath = errorFilePath;
        this.errorMessages = errorMessages != null ? errorMessages : new ArrayList<>();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }

    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public boolean hasErrors() {
        return errorCount > 0;
    }
}
