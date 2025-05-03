package com.prabhu.myapp.pages;

import com.microsoft.playwright.Page;
import com.prabhu.myapp.config.models.ApplicationConfig;
import com.prabhu.myapp.pages.components.HeaderSection;
import jakarta.inject.Inject;

public class BlogPage extends BasePage<BlogPage>{

    public BlogPage(Page page, ApplicationConfig appConfig) {
        super(page, appConfig);
    }


    public HeaderSection header() {
        return headerSection;
    }

    public boolean isBlogPageLoaded() {
        return page.title().contains("Blog - Poco theme"); // Or some visual check
    }
}
