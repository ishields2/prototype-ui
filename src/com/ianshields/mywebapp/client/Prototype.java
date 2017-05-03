package com.ianshields.mywebapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class Prototype implements EntryPoint {
	/**
	 * Universal components for the view
	 */
	private FlowPanel mainPanel = new FlowPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	private Button viewOneButton = new Button();
	private Button viewTwoButton = new Button();
	private Button viewThreeButton = new Button();
	private int viewFrom;

	/**
	 * View 1 components
	 */
	private FlowPanel videoPanel = new FlowPanel();
	private Label titleLabel = new Label();
	private Label bodyLabel = new Label();
	private Frame videoFrame = new Frame();
	private String title;
	private String description;
	private static final String JSON_URL = "http://jsonplaceholder.typicode.com/posts/1";

	/**
	 * View 2 components
	 */
	ScrollPanel sp = new ScrollPanel();
	VerticalPanel vp = new VerticalPanel();
	private JsArray<ImageData> id;
	private static final String JSON_URL_IMAGES = "http://jsonplaceholder.typicode.com/photos";

	/**
	 * View 3 components
	 */
	FlowPanel viewThreeFlowPanel = new FlowPanel();
	FlowPanel header = new FlowPanel();
	FormPanel form = new FormPanel();
	FlowPanel helper = new FlowPanel();
	FlowPanel footer = new FlowPanel();
	Label helperLabel = new Label();
	Label footerLabel = new Label();
	Label headerLabel = new Label();
	TextBox activityName = new TextBox();
	ListBox activityType = new ListBox();
	DateBox dateBox = new DateBox();
	TextArea activityDescription = new TextArea();
	Label activityNameLabel = new Label();
	Label activityTypeLabel = new Label();
	Label dateLabel = new Label();
	Label descriptionLabel = new Label();
	VerticalPanel formPanel = new VerticalPanel();
	Button submitButton = new Button();
	Label activityNameValidationLabel = new Label();
	Label activityTypeValidationLabel = new Label();
	Label activityDateValidationLabel = new Label();
	Label activityDescValidationLabel = new Label();
	Label successLabel = new Label();

	private static final String SUCCESS_MESSAGE = "Thank you, your activity has been successfully submitted.";
	private static final String HELPER_TEXT = "Whether you want to start a new 5 a side team, start a book club or just socialize with your colleagues, here you can submit a listing that will be displayed online for your colleagues to register their interest in!";
	private static final String ACTIVITY_NAME_LABEL_TEXT = "Activity Name: ";
	private static final String ACTIVITY_TYPE_LABEL_TEXT = "Activity Type: ";
	private static final String DATE_TIME_LABEL_TEXT = "Activity Date and Time: ";
	private static final String ACTIVITY_DESC_LABEL_TEXT = "Activity Description: ";
	private static final String SUBMIT_LABEL_TEXT = "Submit";

	/**
	 * Entry point - creates the base layout, retrieves JSON data for view 1 and
	 * loads view 1
	 */
	@Override
	public void onModuleLoad() {
		viewFrom = 1;
		createBaseLayout();
		getVideoData();
		createViewOne();
	}

	/**
	 * Creates the base layout, retrieves JSON data for view 2 and loads view 2
	 */
	public void loadViewTwo() {
		viewFrom = 2;
		createBaseLayout();
		getImageData();
		createViewTwo();
	}

	/**
	 * Creates the base layout, loads view 3
	 */
	public void loadViewThree() {
		viewFrom = 3;
		createBaseLayout();
		createViewThree();
		createViewThreeListeners();
	}

	/**
	 * Assemble the base layout for the main panel and buttons
	 */
	public void createBaseLayout() {
		mainPanel.setStylePrimaryName("main-panel");
		buttonPanel.setStylePrimaryName("button-panel");
		Image img1 = new Image();
		Image img2 = new Image();
		Image img3 = new Image();
		img1.setUrl("https://image.flaticon.com/icons/svg/188/188234.svg");
		img1.setHeight("40px");
		img2.setUrl("https://image.flaticon.com/icons/svg/188/188235.svg");
		img2.setHeight("40px");
		img3.setUrl("https://image.flaticon.com/icons/svg/188/188236.svg");
		img3.setHeight("40px");
		viewOneButton.getElement().appendChild(img1.getElement());
		viewTwoButton.getElement().appendChild(img2.getElement());
		viewThreeButton.getElement().appendChild(img3.getElement());
		addBaseButtonListeners();
		buttonPanel.add(viewOneButton);
		buttonPanel.add(viewTwoButton);
		buttonPanel.add(viewThreeButton);
		viewOneButton.setStylePrimaryName("button1");
		viewTwoButton.setStylePrimaryName("button2");
		viewThreeButton.setStylePrimaryName("button3");
		mainPanel.add(buttonPanel);
		RootLayoutPanel.get().add(mainPanel);
	}

	/**
	 * Adds listeners for base layout
	 */
	private void addBaseButtonListeners() {
		viewOneButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroyView();
				onModuleLoad();
			}
		});
		viewTwoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroyView();
				loadViewTwo();
			}
		});
		viewThreeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroyView();
				loadViewThree();
			}
		});
	}

	/**
	 * Assemble view 1 components
	 */
	private void createViewOne() {
		titleLabel.setText(title);
		titleLabel.setStylePrimaryName("title-label");
		bodyLabel.setText(description);
		bodyLabel.setStylePrimaryName("body-label");
		videoPanel.add(titleLabel);
		videoPanel.add(bodyLabel);
		videoPanel.setStylePrimaryName("video-panel");
		videoPanel.add(videoFrame);
		videoFrame.setStylePrimaryName("video-frame");
		mainPanel.add(videoPanel);
	}

	/**
	 * Assemble view 2 components
	 */
	private void createViewTwo() {
		sp.add(vp);
		sp.setStylePrimaryName("sp-panel");
		vp.setStylePrimaryName("vp-panel");
		mainPanel.add(sp);
	}

	/**
	 * Assemble view 3 components
	 */
	private void createViewThree() {
		header.setStylePrimaryName("header");
		footer.setStylePrimaryName("footer");
		helper.setStylePrimaryName("helper");
		form.setStylePrimaryName("form");
		headerLabel.setText("Create an After Work or Weekend Activity");
		headerLabel.setStylePrimaryName("header-label");
		helperLabel.setText(HELPER_TEXT);
		helperLabel.setStylePrimaryName("helper-text");
		footerLabel.setStylePrimaryName("footer-text");
		successLabel.setText(SUCCESS_MESSAGE);
		successLabel.setStylePrimaryName("success-label");
		header.add(headerLabel);
		helper.add(helperLabel);
		footer.add(successLabel);
		footer.add(footerLabel);
		activityName.setName("activity");
		activityType.setName("type");
		dateBox.getTextBox().setName("date");
		activityDescription.setName("description");
		activityName.setStylePrimaryName("input-box");
		activityDescription.setStylePrimaryName("desc-box");
		dateBox.setStylePrimaryName("date-box");
		activityType.setStylePrimaryName("select-box");
		submitButton.setStylePrimaryName("submit-button");
		successLabel.setStylePrimaryName("success-label");
		submitButton.setText(SUBMIT_LABEL_TEXT);
		activityNameLabel.setText(ACTIVITY_NAME_LABEL_TEXT);
		activityTypeLabel.setText(ACTIVITY_TYPE_LABEL_TEXT);
		dateLabel.setText(DATE_TIME_LABEL_TEXT);
		descriptionLabel.setText(ACTIVITY_DESC_LABEL_TEXT);
		activityNameValidationLabel.setStylePrimaryName("validation-label");
		activityDateValidationLabel.setStylePrimaryName("validation-label");
		activityDescValidationLabel.setStylePrimaryName("validation-label");
		activityNameLabel.setStylePrimaryName("input-label");
		activityTypeLabel.setStylePrimaryName("input-label");
		dateLabel.setStylePrimaryName("input-label");
		descriptionLabel.setStylePrimaryName("input-label");
		successLabel.setVisible(false);
		activityType.addItem("Please Select");
		activityType.addItem("Sports/Health");
		activityType.addItem("Social");
		activityType.addItem("Charity/Fundraising");
		activityType.addItem("Other");
		formPanel.setStylePrimaryName("form-panel");
		formPanel.add(activityNameLabel);
		formPanel.add(activityName);
		formPanel.add(activityNameValidationLabel);
		formPanel.add(activityTypeLabel);
		formPanel.add(activityType);
		formPanel.add(activityTypeValidationLabel);
		formPanel.add(dateLabel);
		formPanel.add(dateBox);
		formPanel.add(activityDateValidationLabel);
		formPanel.add(descriptionLabel);
		formPanel.add(activityDescription);
		formPanel.add(activityDescValidationLabel);
		formPanel.add(submitButton);
		formPanel.add(successLabel);
		form.setAction("");
		form.add(formPanel);
		viewThreeFlowPanel.add(header);
		viewThreeFlowPanel.add(helper);
		viewThreeFlowPanel.add(form);
		viewThreeFlowPanel.add(footer);
		viewThreeFlowPanel.setStylePrimaryName("flow-panel");
		mainPanel.add(viewThreeFlowPanel);
	}

	/**
	 * Add view 3 listeners
	 */
	private void createViewThreeListeners() {
		activityName.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				String input = activityName.getText();
				if (input == null || input.length() > 30 || !input.matches("[a-zA-Z0-9 ]+")) {
					activityNameValidationLabel
							.setText("Enter between 1-30 characters which are letters, numbers or spaces");
					return;
				}
				activityNameValidationLabel.setText(" ");
			}
		});
		dateBox.getTextBox().addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (dateBox.getValue() == null) {
					activityDateValidationLabel.setText("Please select a date");
				} else {
					activityDateValidationLabel.setText(" ");
				}
			}
		});
		activityDescription.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				String input = activityDescription.getText();
				if (input == null || input.length() < 30 || input.length() > 150) {
					activityDescValidationLabel.setText("Enter between 30-150 characters.");
					return;
				}
				activityDescValidationLabel.setText(" ");
			}
		});
		submitButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				if (activityName.getText().length() == 0) {
					Window.alert("Please enter an Activity Name");
					event.cancel();
				}
				if (activityName.getText().length() > 30) {
					Window.alert("Activity name must not exceed 30 characters");
					event.cancel();
				}

				if (!activityName.getText().matches("[a-zA-Z0-9 ]+")) {
					Window.alert("Activity name can only contain spaces letters or numbers");
					event.cancel();
				}
				if (activityType.getName().equalsIgnoreCase("Please Select")) {
					Window.alert("Please select an activity type from the drop down list");
					event.cancel();
				}
				if (dateBox.getValue() == null) {
					Window.alert("Please select an Activity Date");
					event.cancel();
				}
				if (activityDescription.getText().length() < 30 || activityDescription.getText().length() > 150) {
					Window.alert("Activity description must be between 30 and 150 characters in length");
					event.cancel();
				}
			}
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				successLabel.setVisible(true);
			}
		});
	}

	/**
	 * Retrieve JSON data for display in view 1
	 */
	private void getVideoData() {
		String url = JSON_URL;
		// may normally need to encode
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayError("Couldn't retrieve JSON");
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						VideoData vd = JsonUtils.safeEval(response.getText());
						titleLabel.setText(vd.getTitle());
						bodyLabel.setText(vd.getBody());
					} else {
						displayError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON");
		}
	}
	
	/**
	 * Retrieve JSON data for display in view 2
	 */
	private void getImageData() {
		String url = JSON_URL_IMAGES;
		// may normally need to encode
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					displayImageError("Couldn't retrieve JSON");
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						id = JsonUtils.<JsArray<ImageData>> safeEval(response.getText());
						for (int i = 1; i <= 10; i++) {
							final Image image = new Image(id.get(i).getUrl());
							image.setStylePrimaryName("view-2-images");
							image.setTitle("Image Number: " + i);
							image.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									Window.alert(image.getTitle());
								}
							});
							vp.add(image);
							Label imageText = new Label();
							imageText.setText(id.get(i).getTitle());
							imageText.setStylePrimaryName("view-2-text");
							vp.add(imageText);
						}
					} else {
						displayImageError("Couldn't retrieve JSON (" + response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON");
		}
	}
	/**
	 * Display error on view 1 if JSON data cannot be obtained
	 */
	private void displayError(String error) {
		Window.alert(error);
	}
	
	/**
	 * Display error on view 2 if JSON data cannot be obtained
	 */
	private void displayImageError(String error) {
		Window.alert(error);
	}

	/**
	 * Checks for previous view and resets all components
	 */
	private void destroyView() {
		RootLayoutPanel.get().remove(mainPanel);

		// universal components
		mainPanel = new FlowPanel();
		buttonPanel = new HorizontalPanel();
		viewOneButton = new Button();
		viewTwoButton = new Button();
		viewThreeButton = new Button();

		if (viewFrom == 1) {
			// view 1 components
			videoPanel = new FlowPanel();
			titleLabel = new Label();
			bodyLabel = new Label();
			videoFrame = new Frame("https://player.vimeo.com/video/110033541");
		} else if (viewFrom == 2) {
			// view 2 components
			sp = new ScrollPanel();
			vp = new VerticalPanel();
		} else if (viewFrom == 3) {
			// view 3 components
			viewThreeFlowPanel = new FlowPanel();
			header = new FlowPanel();
			form = new FormPanel();
			helper = new FlowPanel();
			footer = new FlowPanel();
			helperLabel = new Label();
			footerLabel = new Label();
			headerLabel = new Label();
			activityName = new TextBox();
			activityType = new ListBox();
			dateBox = new DateBox();
			activityDescription = new TextArea();
			activityNameLabel = new Label();
			activityTypeLabel = new Label();
			dateLabel = new Label();
			descriptionLabel = new Label();
			formPanel = new VerticalPanel();
			submitButton = new Button();
			activityNameValidationLabel = new Label();
			activityTypeValidationLabel = new Label();
			activityDateValidationLabel = new Label();
			activityDescValidationLabel = new Label();
		}
	}
}
