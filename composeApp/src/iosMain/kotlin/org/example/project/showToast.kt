package org.example.project

import platform.UIKit.*
import platform.Foundation.*

actual fun showToast(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    
    alert.addAction(UIAlertAction.actionWithTitle(
        title = "OK",
        style = UIAlertActionStyleDefault,
        handler = null
    ))

    // Ensure this runs on the main thread
    NSOperationQueue.mainQueue.addOperationWithBlock {
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        viewController?.presentViewController(alert, animated = true, completion = null)
    }
}

