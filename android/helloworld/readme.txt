Prueba con Maven.

    https://code.google.com/p/maven-android-plugin/wiki/GettingStarted

        ANDROID_HOME=C:\Ramon\aplicaciones\adt-bundle-windows-x86-20130729\sdk
        %ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools

    Toco el pom.xml de:

        helloflashlight/pom.xml

    con:

        1. artifact/name/version
        2. <platform>4.3</platform>, que es la que me dice que tengo instalado el "Android SDK Manager".

Use the minimal sample from:

    http://stackoverflow.com/questions/11888398/minimal-source-files-to-create-android-app-using-eclipse-adt
    
    Cambié la clase Minimal.java por MinimalWithMaven.java porque si no se pisaba con la antigua Minimal

    Le cambié el AndroidMainfest.xml para:
    
        1. Poner el nombre "MinimalWithMaven" en vez de "Minimal".
        2. Ponerle un "android.intent.category.LAUNCHER" añadiendo la línea:
        
    Y queda:

        <activity android:name="MinimalWithMaven">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>


Hago:

    mvn install

Y ya da OK. Voy a desplegarlo con:

    mvn android:deploy

Para depurar: ejecutar Android Debug Bridge:

	$ adb

Que deja hacer cosas como "adb shell" para conectarse a la shell del terminal Android.

Luego el Dalvik Debug Monitor Server:

	$ ddms

Que enlaza procesos a puertos para hacer "Remote debug" en Eclipse. 8700 el principal y otros secuenciales a partir de 8600 para cada proceso que tiene el terminal Android corriendo.

En Android, arrancar la aplicación "Dev settings", y en la opción "Select debug app" elegir la que queremos depurar, luego marcar "Wait for debugger". Así cuando se arranque la aplicación, pondrá un mensaje pidiendo que se conecte el debugger. En Eclipse crear una configuración de debug "Remote" y poner el puerto 8700; al conectarse la aplicación arrancará y se detendrá en los breakpoints. 
