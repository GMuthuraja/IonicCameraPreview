import { Component } from '@angular/core';
import { OCR, OCRSourceType, OCRResult } from '@ionic-native/ocr/ngx';
declare var CameraPreview: any;

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})

export class HomePage {

  OCRResult: any;

  constructor(private ocr: OCR) { }

  openCameraPreview() {
    CameraPreview.openCamera("opencamera", (response) => {
      console.log(response);
        this.ocr.recText(OCRSourceType.NORMFILEURL, response)
        .then((res: any) => {
          console.log(JSON.stringify(res));
          this.OCRResult = JSON.stringify(res);
        }).catch((error: any) => console.error(error));
    }, (error => {
      console.log(error);
    }));
  }
}

