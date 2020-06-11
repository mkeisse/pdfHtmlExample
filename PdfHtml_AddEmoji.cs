using System.IO;
using iText.Html2pdf;
using iText.IO.Font;
using iText.Kernel.Colors;
using iText.Kernel.Events;
using iText.Kernel.Font;
using iText.Kernel.Pdf;
using iText.Kernel.Pdf.Canvas;
using iText.Kernel.Pdf.Extgstate;
using iText.Layout;
using iText.Layout.Element;
using iText.Layout.Font;
using iText.Layout.Properties;

namespace it7ns.samples.sandbox.pdfhtml.it7kb
{
    public class PdfHtml_AddEmoji
    {
        static string base_uri = "../../pdfhtml/it7kb/",
            font_path = base_uri + "OpenSansEmoji.ttf",
            html_path = base_uri + "emoji.html",
            dest = base_uri + "../../cmpfiles/sandbox/pdfhtml/cmp_emoji.pdf";

        public static void Main(string[] args)
        {
         new PdfHtml_AddEmoji().fromHtml();   
        }
        
        public void fromHtml()
        {
            PdfDocument
                pdf = new PdfDocument(
                    new PdfWriter(dest)); //we use a PdfDocument Object instead of FileStream to write HTML into
            ConverterProperties cprop = new ConverterProperties();
            FontProvider provider = new FontProvider();
            provider.AddFont(font_path, PdfEncodings.IDENTITY_H);

            cprop.SetFontProvider(provider);
            pdf.AddEventHandler(PdfDocumentEvent.END_PAGE, new WatermarkEvent()); //here we add custom event handler
            HtmlConverter.ConvertToPdf(new FileStream(html_path, FileMode.Open), pdf, cprop);
        }

        public void createDoc()
        {
            PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
            Document doc = new Document(pdf);
            FontProvider provider = new FontProvider();

            provider.AddFont(font_path, PdfEncodings.IDENTITY_H);
            PdfFont font = PdfFontFactory.CreateFont(font_path, PdfEncodings.IDENTITY_H, true);
            font.SetSubset(false);
            
            doc.SetFont(font);

            Paragraph p = new Paragraph($"\uD83D\uDE06 This text has an emoji, look! -> {encodeCodePoint(0x1F600)} ");
            doc.Add(p);
            doc.Close();
        }

        private string encodeCodePoint(int codepoint)
        {
            return char.ConvertFromUtf32(codepoint);
        }
    }
}