package com.itextpdf.samples.sandbox.pdfhtml.it7kb;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontProvider;

import java.io.FileInputStream;
import java.io.IOException;

public class PdfHtml_AddEmoji {
    /*IMPORTANT NOTE:
     * If the Typography package is present in the dependencies it will be called in this class and require a license file to be able to run.
     * */
    public static final String base_uri = "src/main/resources/";
    public static final String font_path = base_uri + "font/NotoEmoji-Regular.ttf";
    public static final String html_path = base_uri + "pdfhtml/it7kb/emoji.html";
    public static final String dest_path = "cmpfiles/sandbox/pdfhtml/cmp_emoji.pdf";

    public static void main(String[] args) throws IOException {
        new PdfHtml_AddEmoji().fromHtml();
    }

    void fromHtml() throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest_path));
        ConverterProperties cprop = new ConverterProperties();//ConverterProperties will be used to add the FontProvider to the Converter.
        FontProvider provider = new FontProvider();//The FontProvider will hold our emoji font

        provider.addFont(font_path, PdfEncodings.IDENTITY_H);//add font path to provider
        cprop.setFontProvider(provider);//add provider to properties

        HtmlConverter.convertToPdf(new FileInputStream(html_path), pdf, cprop);//Include ConverterProperties as argument for the #convertToPdf() method.
    }

    public void createEmojiDocument() throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest_path));
        Document doc = new Document(pdf);
        PdfFont emoji_font = PdfFontFactory.createFont(font_path); //Create Pdf Font with Emoji glyphs
        Paragraph p = new Paragraph();

        p.setFont(emoji_font); //add font to Paragraph
        p.setFontSize(15);
        p.add(new Text(String.format("Here are some emojis: %s %s", encodeCodepoint(0x1F600), encodeCodepoint(0x1F604)))); //encode unicode values

        doc.add(p); //add Paragraph to document
        doc.close();
    }

    private String encodeCodepoint(int codePoint) {
        char[] chars = Character.toChars(codePoint);//Create char[] from integer code point
        StringBuilder sb = new StringBuilder();
        for (char ch : chars) {
            sb.append(String.format("\\u%04X", (int) ch)); //append characters in correct format.
        }
        return sb.toString(); //return codepoint
    }
}
