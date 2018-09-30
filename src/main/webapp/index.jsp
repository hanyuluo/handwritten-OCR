<html>
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
   

</head>
<body>

  <nav class="teal lighten-1">
    <div class="nav-wrapper">
      <a href="#" class="brand-logo center">OCR API Test Page</a>
    </div>
  </nav>

<div class="row"></div>


  <form action="test" enctype="multipart/form-data" method="POST">
  	<div class="row">
    <div class="file-field input-field col s8 offset-s1">
      <div class="btn">
        <span>File</span>
        <input type="file" name="file" />
      </div>
      <div class="file-path-wrapper">
        <input class="file-path validate" id="file_upload" type="text" placeholder="Upload an image (max 4MB)">
      </div>
  
    </div>
    
      <div class="input-field col s2">
      	<select name="ocr_option">
          <option value="" disabled selected>Select Option</option>
          <option value="google_vision">Google Vision API</option>
          <option value="tesseract">Tesseract OCR</option>
          <option value="tesseract+omr">Tesseract OCR + Naive OMR</option>
          <option value="icd">ICD (Google/Tesseract)</option>
        </select>
    <label>OCR API</label>
      </div>
    </div>

    <div class="row">  
    <div class="col s2 offset-s5 center-align" > 
    <button class="btn-large waves-effect waves-light" type="submit" name="action">Submit
    </button>
    </div>
    </div>

  </form>
 


</body>

<script>
$(document).ready(function() {
    $('select').material_select();
  });
</script>

</html>
