# Japanese character recognition JAVA Android apps
Japanese character recognition Application developed with Java Android, the model is YOLOv4.
The API can be seen [here](https://github.com/Sekigahara/Japanese-character-recognition-YOLOv4-Flask-API).

## Main Repositories
- High level and low level overview of the project can be found in the main repositories.
- Main respositories can be found [here](https://github.com/Sekigahara/Multilabel-classification-Japanese-character-with-YOLOv4).

## Installation
Recommended to utilizes a real device instead of emulator due to the usage of camera.
- Setup the API first [here](https://github.com/Sekigahara/Japanese-character-recognition-YOLOv4-Flask-API)
- Android studio is important to run and changes the API IP.
- Make sure the developer options in setting is enabled.
- Run and build

## Features
- Take images directly from camera
- Take images from the gallery
- Full image displaying the detection result
- Displaying every detected characters

## Application Architecture
- IDE : Android Studio
- Programming Language = Java with Android packages
- Architecture = MVP
- Third Party libraries = Retrofit, Gson, okhttp3

## Application sample
<b>Tested with Samsung Galaxy J7 Prime with Marshmallow android(Android 8)</b></br>
<table style="width:100%">
  <tr>
    <th>Main Menu page</th>
    <th>Camera Intent #1</th>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/54882818/186601541-9defcc00-3b86-4fa1-99e0-4e395e0dd3be.jpg"/></td>
    <td><img src="https://user-images.githubusercontent.com/54882818/186601708-e837e672-a19d-4533-a638-5b6c4027cf4c.jpg"/></td>
  </tr>
  <tr>
    <th>Camera Intent #2</th>
    <th>Result page</th>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/54882818/186601731-c75212d6-d393-4d75-8bf9-647932105029.jpg"/></td>
    <td><img src="https://user-images.githubusercontent.com/54882818/186601760-b422054b-8083-45ff-bca6-0ed3b8c15cdc.jpg"/></td>
  </tr>
</table>
