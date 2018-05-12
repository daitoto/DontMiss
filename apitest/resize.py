import cv2
import numpy as np

img = cv2.imread('image/7.jpg')
x, y, z = np.shape(img)
if x<y:
	k = y / 480
else:
	k = x / 480
print(x/k, y/k)
img = cv2.resize(img, (int(y / k), int(x / k)))
cv2.imwrite('image/7_1.jpg', img)