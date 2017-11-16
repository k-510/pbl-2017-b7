$(document).ready(function () {

  var invalidFeedBack = {
    message : $('#invalid-feedback-message'),
    userID  : $('#invalid-feedback-userID'),
    password: $('#invalid-feedback-password')
  };
  var formData = {
    userID  : $('#userID'),
    password: $('#password')
  };
  var submitButton = $('#submit');

  var reset = function () {
    invalidFeedBack.message.css('display', 'none');
    invalidFeedBack.userID.text('');
    invalidFeedBack.password.text('');
    formData.userID.removeClass('is-invalid');
    formData.password.removeClass('is-invalid');
  };

  var disableSubmitButton = function () {
    submitButton.removeClass('btn-primary')
                .addClass('btn-secondary')
                .attr('disabled', true);
  };

  var enableSubmitButton = function () {
    submitButton.removeClass('btn-secondary')
                .addClass('btn-primary')
                .removeAttr('disabled');
  };

  var showAlertMessage = function (message) {
    invalidFeedBack.message.css('display', '')
                           .text(message);
  };

  submitButton.on('click', function () {

    reset();

    var isInvalid = false;
    if (formData.userID.val() === '') {
      showAlertMessage('ユーザ ID またはパスワードが空欄です．');
      invalidFeedBack.userID.text('値を入力してください．');
      formData.userID.addClass('is-invalid');
      isInvalid = true;
    }
    if (formData.password.val() === '') {
      showAlertMessage('ユーザ ID またはパスワードが空欄です．');
      invalidFeedBack.password.text('値を入力してください．');
      formData.password.addClass('is-invalid');
      isInvalid = true;
    }
    if (isInvalid) { return; }

    disableSubmitButton();

    $.ajax({
      type    : 'POST',
      url     : '/pbl-2017-b7/api/login',
      data: {
        user_id : formData.userID.val(),
        password: formData.password.val()
      }
    }).done(function (data, textStatus, jqXHR) {
      document.cookie = 'kuishiro-session=' + data.session_token;
      location.href = '/pbl-2017-b7/my/top.html';
    }).fail(function (jqXHR, textStatus, errorThrown) {
      if (jqXHR.status === 403) {
        showAlertMessage('ユーザ名またはパスワードが違います．');
      } else {
        showAlertMessage('エラーが発生しました．');
        console.error(jqXHR);
      }
      enableSubmitButton();
    });

  });

  reset();

});
