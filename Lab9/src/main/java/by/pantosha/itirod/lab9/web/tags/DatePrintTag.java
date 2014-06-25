package by.pantosha.itirod.lab9.web.tags;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class DatePrintTag extends SimpleTagSupport {

    private Date _date;

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        _date = date;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        Calendar date = Calendar.getInstance();
        date.setTime(_date);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        Calendar today = Calendar.getInstance();

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        if (sameDays(date, yesterday)) {
            out.write("yesterday");
        } else if (sameDays(date, today)) {
            out.write("today");
        } else if (sameDays(date, tomorrow)) {
            out.write("tomorrow");
        } else if (sameYears(date, today)) {
            int daysAgo = today.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR);
            if (daysAgo > 0 )
                out.write(daysAgo + " days ago");
            else
                out.write(Math.abs(daysAgo) + " days in future");
        }
    }

    private boolean sameDays(Calendar what, Calendar with) {
        return what.get(Calendar.YEAR) == with.get(Calendar.YEAR)
                && what.get(Calendar.DAY_OF_YEAR) == with.get(Calendar.DAY_OF_YEAR);
    }

    private boolean sameYears(Calendar what, Calendar with) {
        return what.get(Calendar.YEAR) == with.get(Calendar.YEAR);
    }
}
