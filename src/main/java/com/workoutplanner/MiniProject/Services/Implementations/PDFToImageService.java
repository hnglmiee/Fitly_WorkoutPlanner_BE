package com.workoutplanner.MiniProject.Services.Implementations;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// PDF InBody là dạng image PDF
// Tesseract chỉ đọc được hình → phải convert từng trang PDF thành ảnh trước

@Service
public class PDFToImageService {

    public List<BufferedImage> convertPdfToImages(MultipartFile pdfFile, int dpi) throws IOException {

        // Dùng PDFBox render từng page thành BufferedImage
        // Load file PDF thông qua byte array
        // PDDocument.load() sẽ parse toàn bộ PDF vào RAM
        try (PDDocument document = PDDocument.load(pdfFile.getBytes())) {

            // Tạo đối tượng renderer để render từng trang PDF thành ảnh.
            PDFRenderer renderer = new PDFRenderer(document);

            // Tạo list để chứa ảnh của từng trang.
            List<BufferedImage> pages = new ArrayList<>();

            // Lấy tổng số trang trong PDF.
            int totalPages = document.getNumberOfPages();

            // Lặp qua từng trang PDF.
            for (int i = 0; i < totalPages; i++) {
                // Render trang thứ i thành hình ảnh.
                // i = số trang (0-based index)
                // dpi = độ phân giải (300 giúp OCR sắc nét)
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                // Kết quả trả về là 1 ảnh dưới dạng BufferedImage.
                // Thêm ảnh vừa tạo vào danh sách.
                pages.add(image);
            }

            return pages;
        }
    }

    public List<BufferedImage> convertPdfToImages(MultipartFile pdfFile) throws IOException {
        return convertPdfToImages(pdfFile, 300);
    }
}
