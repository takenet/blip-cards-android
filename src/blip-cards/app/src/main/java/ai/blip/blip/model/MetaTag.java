package ai.blip.blip.model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by dirceubelem on 11/09/17.
 */

public class MetaTag {

    private String title;
    private String description;
    private String image;

    public MetaTag(String url) {

        try {
            // Parse og tags
            Document doc = Jsoup.connect(url).get();
            Elements ogTags = doc.select("meta[property^=og:]");
            if (ogTags.size() <= 0) {
                return;
            }

            for (int i = 0; i < ogTags.size(); i++) {
                Element tag = ogTags.get(i);

                String text = tag.attr("property");
                if ("og:image".equals(text)) {
                    image = tag.attr("content");
                } else if ("og:description".equals(text)) {
                    description = tag.attr("content");
                } else if ("og:title".equals(text)) {
                    title = tag.attr("content");
                }
            }

        } catch (Exception e) {
            Log.e("Erro", e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
