package org.knime.moodle.internals.logs;

import static org.knime.moodle.internals.logs.Component.*;
import static org.knime.moodle.internals.logs.ComponentEvent.get;
import static org.knime.moodle.internals.logs.Event.*;

import java.util.HashMap;
import java.util.Map;


public class LogDescription {
	
	private static final Map<ComponentEvent, LogParameters[]> LOGTYPES = new HashMap<>();

	static{
		
		LOGTYPES.put(get(ACTIVITY_REPORT, ACTIVITY_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(ACTIVITY_REPORT, OUTLINE_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.COURSE});
		
		LOGTYPES.put(get(ASSIGNMENT, A_SUBMISSION_HAS_BEEN_SUBMITTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, ALL_THE_SUBMISSIONS_ARE_BEING_DOWNLOADED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, AN_EXTENSION_HAS_BEEN_GRANTED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, ASSIGNMENT_OVERRIDE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE, LogParameters.GROUP});
		LOGTYPES.put(get(ASSIGNMENT, ASSIGNMENT_OVERRIDE_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE, LogParameters.GROUP});
		LOGTYPES.put(get(ASSIGNMENT, ASSIGNMENT_OVERRIDE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE, LogParameters.GROUP});
		LOGTYPES.put(get(ASSIGNMENT, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE, LogParameters.COURSE});
		
		LOGTYPES.put(get(ASSIGNMENT, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, FEEDBACK_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		
				
		LOGTYPES.put(get(ASSIGNMENT, GRADING_FORM_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, GRADING_TABLE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, REMOVE_SUBMISSION_CONFIRMATION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, SUBMISSION_CONFIRMATION_FORM_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, SUBMISSION_FORM_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, SUBMISSION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, THE_STATE_OF_THE_WORKFLOW_HAS_BEEN_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, THE_STATUS_OF_THE_SUBMISSION_HAS_BEEN_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE, LogParameters.SUBITEM});
		
		LOGTYPES.put(get(ASSIGNMENT, THE_STATUS_OF_THE_SUBMISSION_HAS_BEEN_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, THE_SUBMISSION_HAS_BEEN_GRADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER,  LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, THE_SUBMISSIONS_HAVE_BEEN_LOCKED_FOR_A_USER), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER,  LogParameters.MODULE});
		LOGTYPES.put(get(ASSIGNMENT, THE_SUBMISSIONS_HAVE_BEEN_UNLOCKED_FOR_A_USER), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER,  LogParameters.MODULE});
		
		LOGTYPES.put(get(ASSIGNMENT, THE_USER_HAS_ACCEPTED_THE_STATEMENT_OF_THE_SUBMISSION), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(BOOK, CHAPTER_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(BOOK, CHAPTER_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(BOOK, CHAPTER_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(BOOK, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(BOOK_PRINTING, BOOK_PRINTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(CHAT, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHAT, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHAT, MESSAGE_SENT),  new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHAT, SESSIONS_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		
		LOGTYPES.put(get(CHOICE, CHOICE_ANSWER_ADDED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHOICE, CHOICE_MADE), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(CHOICE, CHOICE_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHOICE, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(CHOICE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		
		LOGTYPES.put(get(COMMENTS, COMMENT_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});
		LOGTYPES.put(get(COMMENTS, COMMENT_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});

		LOGTYPES.put(get(COURSE_COMPLETION, COMPLETION_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});

		LOGTYPES.put(get(COURSE_PARTICIPATION, PARTICIPATION_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});

		LOGTYPES.put(get(DATABASE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, FIELD_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, FIELD_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, RECORD_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, RECORD_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, RECORD_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, TEMPLATE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(DATABASE, TEMPLATES_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		
		
		LOGTYPES.put(get(EVENT_MONITOR, RULE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(EVENT_MONITOR, SUBSCRIPTION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});

		LOGTYPES.put(get(EXCEL_SPREADSHEET, XLS_GRADE_EXPORTED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(EXTERNAL_TOOL, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(FEEDBACK, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FEEDBACK, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FEEDBACK, RESPONSE_SUBMITTED), new LogParameters[] {LogParameters.EMPTY});

		LOGTYPES.put(get(FILE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(FILE_SUBMISSIONS, A_FILE_HAS_BEEN_UPLOADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FILE_SUBMISSIONS, SUBMISSION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FILE_SUBMISSIONS, SUBMISSION_CREATED2), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FILE_SUBMISSIONS, SUBMISSION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FILE_SUBMISSIONS, SUBMISSION_UPDATED2), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(FOLDER, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FOLDER, FOLDER_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FOLDER, ZIP_ARCHIVE_OF_FOLDER_DOWNLOADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(FORUM, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, COURSE_SEARCHED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, DISCUSSION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, DISCUSSION_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, DISCUSSION_MOVED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, DISCUSSION_PINNED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, DISCUSSION_SUBSCRIPTION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, DISCUSSION_SUBSCRIPTION_DELETED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, DISCUSSION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, POST_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.SUBITEM, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, POST_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.SUBITEM, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, POST_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.SUBITEM, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, SOME_CONTENT_HAS_BEEN_POSTED), new LogParameters[] {LogParameters.USER, LogParameters.SUBITEM, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, SUBSCRIBERS_VIEWED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, SUBSCRIPTION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.MODULE});
		LOGTYPES.put(get(FORUM, SUBSCRIPTION_DELETED), new LogParameters[] {LogParameters.EMPTY});
		LOGTYPES.put(get(FORUM, USER_REPORT_VIEWED), new LogParameters[] {LogParameters.EMPTY});

  	    LOGTYPES.put(get(GLOSSARY, CATEGORY_HAS_BEEN_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(GLOSSARY, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(GLOSSARY, ENTRY_HAS_BEEN_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(GLOSSARY, ENTRY_HAS_BEEN_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(GLOSSARY, ENTRY_HAS_BEEN_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(GRADE_HISTORY, GRADE_HISTORY_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(GRADER_REPORT, GRADER_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});
		
		LOGTYPES.put(get(H5P, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
	  	LOGTYPES.put(get(H5P, XAPI_STATEMENT_RECEIVED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(H5P_PACKAGE, H5P_CONTENT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(HOTPOT_MODULE, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(HOTPOT_MODULE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(HOTPOT_MODULE, HOTPOT_ATTEMPT_STARTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(HOTPOT_MODULE, HOTPOT_ATTEMPT_SUBMITTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(IMS_CONTENT_PACKAGE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(JOURNAL, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(JOURNAL, JOURNAL_ENTRIES_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(JOURNAL, JOURNAL_ENTRY_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(JOURNAL, JOURNAL_ENTRY_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(KALTURA_MEDIA_ASSIGNMENT, ASSIGNMENT_DETAILS_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(KALTURA_MEDIA_ASSIGNMENT, ASSIGNMENT_SUBMITTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(KALTURA_MEDIA_ASSIGNMENT, GRADE_SUBMISSIONS_PAGE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(KALTURA_MEDIA_ASSIGNMENT, SINGLE_SUBMISSION_PAGE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(LESSON, CONTENT_PAGE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(LESSON, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, LESSON_ENDED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, LESSON_STARTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, PAGE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, PAGE_MOVED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, PAGE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, LESSON_RESTARTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, LESSON_RESUMED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, QUESTION_ANSWERED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(LESSON, QUESTION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(LIVE_LOGS, LIVE_LOG_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(LOGS, LOG_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(LOGS, USER_LOG_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(ONLINE_TEXT_SUBMISSIONS, AN_ONLINE_TEXT_HAS_BEEN_UPLOADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ONLINE_TEXT_SUBMISSIONS, SUBMISSION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ONLINE_TEXT_SUBMISSIONS, SUBMISSION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ONLINE_TEXT_SUBMISSIONS, SUBMISSION_CREATED2), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(ONLINE_TEXT_SUBMISSIONS, SUBMISSION_UPDATED2), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

	    LOGTYPES.put(get(OPENDOCUMENT_SPREADSHEET, OPENDOCUMENT_GRADE_EXPORTED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(OUTCOMES_REPORT, GRADE_OUTCOMES_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(OVERVIEW_REPORT, GRADE_OVERVIEW_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(PAGE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(PLAIN_TEXT_FILE, TXT_GRADE_EXPORTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(QUIZ, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUESTION_MANUALLY_GRADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.SUBITEM,LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_ABANDONED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_PREVIEW_STARTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_REVIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_STARTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_SUBMITTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_SUMMARY_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_TIME_LIMIT_EXCEEDED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_ATTEMPT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_EDIT_PAGE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_OVERRIDE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_OVERRIDE_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_OVERRIDE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(QUIZ, QUIZ_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(RECYCLE_BIN, ITEM_DELETED), new LogParameters[] {LogParameters.ITEM});
		
		LOGTYPES.put(get(SCORM_PACKAGE, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SCORM_PACKAGE, SCO_LAUNCHED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SCORM_PACKAGE, SUBMITTED_SCORM_RAW_SCORE), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SCORM_PACKAGE, SUBMITTED_SCORM_STATUS), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(SINGLE_VIEW, GRADE_SINGLE_VIEW_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(STATISTICS, USER_STATISTICS_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(SUBMISSION_COMMENTS, COMMENT_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(SUBMISSION_COMMENTS, COMMENT_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(SURVEY, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SURVEY, SURVEY_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});
		LOGTYPES.put(get(SURVEY, SURVEY_RESPONSE_SUBMITTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(SYSTEM, BADGE_LISTING_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, CALENDAR_EVENT_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CALENDAR_EVENT_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CALENDAR_EVENT_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CALENDAR_SUBSCRIPTION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CONTENT_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CONTENT_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, CONTENT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, COURSE_ACTIVITY_COMPLETION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE, LogParameters.TARGET_USER});
		
		LOGTYPES.put(get(SYSTEM, COURSE_BACKUP_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_COMPLETED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_COMPLETION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_CONTENT_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_MODULE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, COURSE_MODULE_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, COURSE_MODULE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, COURSE_RESET_ENDED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_RESET_STARTED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_RESTORED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_SECTION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_SECTION_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_SECTION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_SUMMARY_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_USER_REPORT_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, COURSE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		  
		LOGTYPES.put(get(SYSTEM, ENROLMENT_INSTANCE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, ENROLMENT_INSTANCE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, EVIDENCE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(SYSTEM, GRADE_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER, LogParameters.SUBITEM});
		LOGTYPES.put(get(SYSTEM, GRADE_ITEM_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, GRADE_ITEM_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, GROUP_ASSIGNED_TO_GROUPING), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUP_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUP_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUP_MEMBER_ADDED), new LogParameters[]  {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUP_MEMBER_REMOVED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUP_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUPING_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.GROUP});
		LOGTYPES.put(get(SYSTEM, GROUPING_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.GROUP});
		
		LOGTYPES.put(get(SYSTEM, NOTE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER});
		LOGTYPES.put(get(SYSTEM, NOTES_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER});
		LOGTYPES.put(get(SYSTEM, QUESTION_CATEGORY_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(SYSTEM, QUESTION_CATEGORY_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(SYSTEM, QUESTION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(SYSTEM, QUESTION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(SYSTEM, QUESTION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		LOGTYPES.put(get(SYSTEM, QUESTIONS_IMPORTED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		  
		LOGTYPES.put(get(SYSTEM, RECENT_ACTIVITY_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		  
		LOGTYPES.put(get(SYSTEM, ROLE_ASSIGNED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER});
		
		LOGTYPES.put(get(SYSTEM, ROLE_CAPABILITIES_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER});
		LOGTYPES.put(get(SYSTEM, ROLE_UNASSIGNED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER});
		LOGTYPES.put(get(SYSTEM, SCALE_CREATED), new LogParameters[] {LogParameters.USER});
		  
		LOGTYPES.put(get(SYSTEM, TAG_ADDED_TO_AN_ITEM), new LogParameters[] {LogParameters.USER, LogParameters.ITEM});
		
		LOGTYPES.put(get(SYSTEM, USER_COMPETENCY_RATED_IN_COURSE), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, USER_COMPETENCY_VIEWED_IN_A_COURSE), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.COURSE});
		
		LOGTYPES.put(get(SYSTEM, USER_ENROLLED_IN_COURSE), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, USER_GRADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.TARGET_USER, LogParameters.SUBITEM});
		  
		LOGTYPES.put(get(SYSTEM, USER_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.COURSE});
		
		LOGTYPES.put(get(SYSTEM, USER_PROFILE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.COURSE});
		LOGTYPES.put(get(SYSTEM, USER_UNENROLLED_FROM_COURSE), new LogParameters[] {LogParameters.USER, LogParameters.TARGET_USER, LogParameters.COURSE});

		LOGTYPES.put(get(TAB_DISPLAY, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		LOGTYPES.put(get(TURNITIN_ASSIGNMENT_2, ADD_SUBMISSION), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(TURNITIN_ASSIGNMENT_2, LIST_SUBMISSIONS), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(URL, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});

		LOGTYPES.put(get(USER_REPORT, GRADE_USER_REPORT_VIEWED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(USER_TOURS, STEP_SHOWN), new LogParameters[] {LogParameters.USER});
		LOGTYPES.put(get(USER_TOURS, TOUR_ENDED), new LogParameters[] {LogParameters.USER});
		LOGTYPES.put(get(USER_TOURS, TOUR_STARTED), new LogParameters[] {LogParameters.USER});

		LOGTYPES.put(get(WIKI, COMMENT_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, COMMENTS_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_HISTORY_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_PAGE_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_PAGE_LOCKS_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_PAGE_MAP_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_PAGE_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WIKI, WIKI_PAGE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(KALTURA_MEDIA_ASSIGNMENT, ASSIGNMENT_DETAILS_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(KALTURA_VIDEO_RESOURCE, VIDEO_RESOURCE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		
		LOGTYPES.put(get(WORKSHOP, A_SUBMISSION_HAS_BEEN_UPLOADED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, ASSESSMENT_EVALUATED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, COURSE_MODULE_INSTANCE_LIST_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, COURSE_MODULE_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, PHASE_SWITCHED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_ASSESSED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_CREATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_DELETED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_REASSESSED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_UPDATED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});
		LOGTYPES.put(get(WORKSHOP, SUBMISSION_VIEWED), new LogParameters[] {LogParameters.USER, LogParameters.ITEM, LogParameters.MODULE});

		LOGTYPES.put(get(XML_FILE, XML_GRADE_EXPORTED), new LogParameters[] {LogParameters.USER, LogParameters.MODULE});
		
		
	}

	/**
	 * Devuelve un array de parámetros según componente y evento
	 * Si no existe devuelve un array con parámetros EMPTY
	 * 
	 * @param component 
	 *            el componente del log
	 * @param eventName
	 *            evento del log
	 * @return LogParameters
	 */
	public static LogParameters[] getLogParamtersList(Component component, Event eventName) {
		return LOGTYPES.getOrDefault(get(component, eventName), new LogParameters[] { LogParameters.EMPTY }  );
	}
	
	
	private LogDescription() {
		throw new UnsupportedOperationException();
	}
}