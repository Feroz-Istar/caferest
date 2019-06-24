<html lang="en">
  <head>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="48730166379.apps.googleusercontent.com">
     <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
  </script>
  <script src="https://apis.google.com/js/client:platform.js?onload=start" async defer>
  </script>
  </head>
  <body>
<button id="signinButton">Sign in with Google</button>
    <script>
    function start() {
        gapi.load('auth2', function() {
          auth2 = gapi.auth2.init({
            client_id: '43831238249-cf2mpekcjf7ffkk51h3eh41jj3u2emt1.apps.googleusercontent.com',
            // Scopes to request in addition to 'profile' and 'email'
            scope: 'https://www.googleapis.com/auth/drive.file'
          });
        });
      }
    
    $('#signinButton').click(function() {
        // signInCallback defined in step 6.
        auth2.grantOfflineAccess().then(signInCallback);
      });
    </script>
    
    
    <script>
function signInCallback(authResult) {
	console.log(authResult);
	
	console.log(authResult['code'])
  if (authResult['code']) {
	
    // Hide the sign-in button now that the user is authorized, for example:
    $('#signinButton').attr('style', 'display: none');

    // Send the code to the server
    $.ajax({
      type: 'POST',
      url: 'https://e83af79b.ngrok.io/caferest/android',
      // Always include an `X-Requested-With` header in every AJAX request,
      // to protect against CSRF attacks.
      headers: {
        'X-Requested-With': 'XMLHttpRequest'
      },
      contentType: 'application/octet-stream; charset=utf-8',
      success: function(result) {
        // Handle or verify the server response.
      },
      processData: false,
      data: authResult['code']
    });
  } else {
    // There was an error.
  }
}
</script>
  </body>
</html>