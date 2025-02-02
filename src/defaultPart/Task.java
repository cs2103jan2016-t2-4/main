package defaultPart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

//@@author A0135766W
public class Task implements Cloneable {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");

	private String _description = "";
	private boolean _isCompleted = false;

	private Calendar _startDateAndTime = new GregorianCalendar();
	private Calendar _endDateAndTime = new GregorianCalendar();

	private boolean _isStartDateSet = false;
	private boolean _isEndDateSet = false;
	private boolean _isStartTimeSet = false;
	private boolean _isEndTimeSet = false;

	private int _recurField;
	private int _recurFrequency = 0;

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public boolean isCompleted() {
		return _isCompleted;
	}

	public void toggleCompleted() {
		_isCompleted = !_isCompleted;
	}

	public boolean isStartDateSet() {
		return _isStartDateSet;
	}

	public boolean isStartTimeSet() {
		return _isStartTimeSet;
	}

	public boolean isEndDateSet() {
		return _isEndDateSet;
	}

	public void unsetEndDate() {
		_isEndDateSet = false;
	}

	public boolean isEndTimeSet() {
		return _isEndTimeSet;
	}

	public Calendar getStartDate() {
		assert _isStartDateSet;
		return (Calendar) _startDateAndTime.clone();
	}

	public Calendar getEndDate() {
		assert _isEndDateSet;
		return (Calendar) _endDateAndTime.clone();
	}

	public void setStartDate(Calendar date) {
		setDateOnly(_startDateAndTime, date);
		_isStartDateSet = true;
	}

	public void setEndDate(Calendar date) {
		setDateOnly(_endDateAndTime, date);
		_isEndDateSet = true;
	}

	public void setStartTime(Calendar date) {
		setTimeOnly(_startDateAndTime, date);
		_isStartTimeSet = true;
	}

	public void setEndTime(Calendar date) {
		setTimeOnly(_endDateAndTime, date);
		_isEndTimeSet = true;
	}

	private void setDateOnly(Calendar destination, Calendar source) {
		destination.set(Calendar.YEAR, source.get(Calendar.YEAR));
		destination.set(Calendar.DAY_OF_YEAR, source.get(Calendar.DAY_OF_YEAR));
	}

	public void setTimeOnly(Calendar destination, Calendar source) {
		destination.set(Calendar.MINUTE, source.get(Calendar.MINUTE));
		destination.set(Calendar.HOUR_OF_DAY, source.get(Calendar.HOUR_OF_DAY));
	}

	public String getFormattedStartDate() {
		assert _isStartDateSet;
		return dateFormat.format(_startDateAndTime.getTime());
	}

	public String getFormattedEndDate() {
		assert _isEndDateSet;
		return dateFormat.format(_endDateAndTime.getTime());
	}

	public String getFormattedStartTime() {
		assert _isStartTimeSet;
		return timeFormat.format(_startDateAndTime.getTime());
	}

	public String getFormattedEndTime() {
		assert _isEndTimeSet;
		return timeFormat.format(_endDateAndTime.getTime());
	}

	public void setStartDateFromFormattedString(String dateString) throws ParseException {
		setStartDate(getDateFromFormattedString(dateString));
	}

	public void setEndDateFromFormattedString(String dateString) throws ParseException {
		setEndDate(getDateFromFormattedString(dateString));
	}

	public void setStartTimeFromFormattedString(String timeString) throws ParseException {
		setStartTime(getTimeFromFormattedString(timeString));
	}

	public void setEndTimeFromFormattedString(String timeString) throws ParseException {
		setEndTime(getTimeFromFormattedString(timeString));
	}

	public Calendar getDateFromFormattedString(String dateString) throws ParseException {
		return getDateOrTimeFromFormattedString(dateString, dateFormat);
	}

	public Calendar getTimeFromFormattedString(String timeString) throws ParseException {
		return getDateOrTimeFromFormattedString(timeString, timeFormat);
	}

	private Calendar getDateOrTimeFromFormattedString(String calendarString,
			SimpleDateFormat simpleDateFormat) throws ParseException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(simpleDateFormat.parse(calendarString));
		return calendar;
	}

	public int getRecurField() {
		return _recurField;
	}

	public void setRecurField(int recurField) {
		_recurField = recurField;
	}

	public int getRecurFrequency() {
		return _recurFrequency;
	}

	public void setRecurFrequency(int recurFrequency) {
		_recurFrequency = recurFrequency;
	}

	public boolean isRecurSet() {
		return _recurFrequency > 0;
	}

	/* Use for ordering of the task list */
	public boolean isDateTimeAfterTask(Task task) {
		if (!task.isStartDateSet()) {
			return false;
		}
		if (!this.isStartDateSet()) {
			return true;
		}

		int compareDateResult = compareDate(this.getStartDate(), task.getStartDate());
		if (compareDateResult != 0) {
			return compareDateResult > 0;
		}

		if (!this.isStartTimeSet()) {
			return false;
		}
		if (!task.isStartTimeSet()) {
			return true;
		}
		return compareTime(this.getStartDate(), task.getStartDate()) > 0;
	}

	public int compareStartAndEndDate(Calendar date) {
		if (isEndDateSet()) {
			return compareDate(_endDateAndTime, date);
		} else {
			return compareDate(_startDateAndTime, date);
		}
	}

	public static int compareDate(Calendar date1, Calendar date2) {
		assert date1 != null && date2 != null;
		int year1 = date1.get(Calendar.YEAR);
		int year2 = date2.get(Calendar.YEAR);
		if (year1 != year2) {
			return year1 - year2;
		}

		return date1.get(Calendar.DAY_OF_YEAR) - date2.get(Calendar.DAY_OF_YEAR);
	}

	private int compareTime(Calendar time1, Calendar time2) {
		assert time1 != null && time2 != null;
		int hour1 = time1.get(Calendar.HOUR_OF_DAY);
		int hour2 = time2.get(Calendar.HOUR_OF_DAY);
		if (hour1 != hour2) {
			return hour1 - hour2;
		}

		return time1.get(Calendar.MINUTE) - time2.get(Calendar.MINUTE);
	}

	public String getDateTimeString() {
		String dateTimeString = "";
		if (isStartDateSet()) {
			dateTimeString += getFormattedStartDate() + " ";

			if (isStartTimeSet()) {
				dateTimeString += getFormattedStartTime() + " ";
			}

			if (isEndDateSet()) {
				dateTimeString += getFormattedEndDate() + " ";
			}

			if (isEndTimeSet()) {
				dateTimeString += getFormattedEndTime() + " ";
			}
		}
		return dateTimeString;
	}

	public String getRecurString() {
		String recurString = "";
		if (isRecurSet()) {
			recurString += _recurFrequency;
			switch (_recurField) {
				case Calendar.DAY_OF_YEAR :
					recurString += "d";
					break;

				case Calendar.WEEK_OF_YEAR :
					recurString += "w";
					break;

				case Calendar.MONTH :
					recurString += "m";
					break;

				case Calendar.YEAR :
					recurString += "y";
					break;
			}
			assert recurString != "";
		}
		return recurString;
	}

	public boolean setStartDateAfterRecur(Calendar date) {
		assert isRecurSet();
		do {
			Calendar nextDate = getNextRecur();
			if (nextDate == null) {
				return false;
			}
			setStartDate(nextDate);
		} while (compareDate(_startDateAndTime, date) <= 0);
		return true;
	}

	// @@author A0135810N
	public Calendar getNextRecur() {
		if (!isStartDateSet() || !isRecurSet()) {
			return null;
		}

		Calendar nextDate = (Calendar) getStartDate().clone();

		incrementNextDate(nextDate);

		if (isEndDateSet() && nextDateAfterEndDate(nextDate)) {
			return null;
		}
		return nextDate;
	}

	private void incrementNextDate(Calendar nextDate) {
		nextDate.add(_recurField, _recurFrequency);
	}

	private boolean nextDateAfterEndDate(Calendar nextDate) {
		return compareDate(nextDate, this.getEndDate()) > 0;
	}

	public boolean willRecur() {
		return (this.getNextRecur() != null);
	}

	@Override
	public Task clone() {
		try {
			Task task = (Task) super.clone();
			task._startDateAndTime = (Calendar) this._startDateAndTime.clone();
			task._endDateAndTime = (Calendar) this._endDateAndTime.clone();
			return task;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return _description + " " + getDateTimeString() + getRecurString();
	}
}
