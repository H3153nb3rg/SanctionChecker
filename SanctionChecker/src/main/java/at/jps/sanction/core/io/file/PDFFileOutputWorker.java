/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.io.file;

import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import at.jps.sanction.model.AnalysisResult;
import at.jps.sanction.model.HitResult;

public class PDFFileOutputWorker extends FileOutputWorker {

    static final Logger logger = LoggerFactory.getLogger(PDFFileOutputWorker.class);

    public PDFFileOutputWorker() {
        super();
    }

    @Override
    public void initialize() {
    }

    @Override
    protected String getExtension() {
        return ".pdf";
    }

    @Override
    public void handleMessage(final AnalysisResult message) {
        try {

            logger.info("write Message: " + message.getMessage().getId());

            final Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(getFilename()));
            document.open();

            final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
            final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL);

            Chunk chunk = new Chunk("Message", chapterFont);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph(message.getMessage().toString(), paragraphFont));
            document.add(chapter);

            getWriter().write(message.getMessage().toString());

            if (message.getHitList() != null) {

                chapter.add(new Paragraph("Hits", paragraphFont));

                for (final HitResult hit : message.getHitList()) {
                    final PdfPTable table = new PdfPTable(2);

                    table.addCell("Description");
                    table.addCell(hit.getHitDescripton());
                    table.addCell("Field");
                    table.addCell(hit.getHitDescripton());
                    table.addCell("Absolute Value");
                    table.addCell(hit.getAbsolutHit() + "");
                    table.addCell("Relative Value");
                    table.addCell(hit.getRelativeHit() + "");
                    table.addCell("Phrase Value");
                    table.addCell(hit.getPhraseHit() + "");
                    table.addCell("HitType");
                    table.addCell(hit.getHitType());

                    document.add(table);
                }
            }
            else {
                final String exception = message.getException();
                if (exception != null) {
                    chunk = new Chunk("Error:", chapterFont);
                    chapter = new Chapter(new Paragraph(chunk), 1);
                    chapter.add(new Paragraph(message.getException().toString(), paragraphFont));
                    document.add(chapter);
                }
            }
            document.close();
        }
        catch (final Exception e) {
            logger.error("Error writing to file:" + getFilename());
            logger.debug("Exception: ", e);
        }
    }

}
