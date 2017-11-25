package app.test.com.testapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import app.test.com.testapp.activities.SearchActivity;
import app.test.com.testapp.models.BookImageLinksModel;
import app.test.com.testapp.models.BookInfoModel;
import app.test.com.testapp.models.BookModel;
import app.test.com.testapp.utils.VersionUtils;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {

        // context of the app under test..
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("app.test.com.testapp", appContext.getPackageName());
    }

    @Test
    public void checkBookModel() throws Exception {

        BookModel model = new BookModel();
        model.setId("1");
        model.setSelfLink("link");

        BookInfoModel bookInfoModel = new BookInfoModel();
        bookInfoModel.setTitle("title");
        bookInfoModel.setPublishedDate("2010");

        // not valid, missing book info..
        assertThat(model.isValid(), is(false));

        ArrayList<String> authors = new ArrayList<>();
        authors.add("author 1");
        authors.add("author 2");
        bookInfoModel.setAuthors(authors);

        BookImageLinksModel imageLinksModel = new BookImageLinksModel();
        imageLinksModel.setThumbnail("www.thumbnail.com/test.png");
        bookInfoModel.setImageLinks(imageLinksModel);

        model.setVolumeInfo(bookInfoModel);

        // valid..
        assertThat(model.isValid(), is(true));
    }

    @Test
    public void checkVersion() throws Exception {

        assertNotEquals(VersionUtils.isLollipop(), VersionUtils.hasMarshMallow());
    }

    @Test
    public void checkRootView() throws Exception {

        SearchActivity activity = new SearchActivity();
        activity.setContentView(R.layout.activity_search);
        assertThat(activity.getRootView(), is(not(null)));
    }
}
