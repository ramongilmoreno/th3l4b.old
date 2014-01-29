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

Arranco el Android SDK con:

	$ android

Abro el menú Tools > Manage ADV

Selecciono "Nexus 4" (lo he creado previamente), y pulso "Start"

Para depurar: creía que hacía falta ejecutar Android Debug Bridge, pero no hace nada:

	$ adb -e

Que deja hacer cosas como "adb shell" para conectarse a la shell del terminal Android.

Luego el Dalvik Debug Monitor Server:

	$ ddms

Que enlaza procesos a puertos para hacer "Remote debug" en Eclipse. 8700 el principal y otros secuenciales a partir de 8600 para cada proceso que tiene el terminal Android corriendo.

En Android, arrancar la aplicación "Dev settings", y en la opción "Select debug app" elegir la que queremos depurar, luego marcar "Wait for debugger". Así cuando se arranque la aplicación, pondrá un mensaje pidiendo que se conecte el debugger. En Eclipse crear una configuración de debug "Remote" y poner el puerto 8700; al conectarse la aplicación arrancará y se detendrá en los breakpoints. 


Para depurar la BBDD:

	$ adb shell

Nos lleva a la shell. Vamos al directorio de la BBDD:

	$ cd /data/data/com.th3l4b.android.testbed/databases

Y ahí abrimos sqlite3 en el fichero que hay

	$ sqlite3 ShoppingDatabase
	SQLite version 3.7.11 2012-03-20 11:35:50
	Enter ".help" for instructions
	Enter SQL statements terminated with a ";"
	sqlite> select * from Need;
	255d7e8b-551f-4720-ae81-dd10fdff8630|P||cf69da14-3824-48ed-8c52-1bd7d7104c1c
	d206d098-b701-401f-87d5-cf572e66812c|P||979c90d8-15f1-44e2-bad0-da217ed4ea66
	342a132f-53cb-430f-80bf-2e7a9af49daf|P||db1becae-0ffc-4f16-b83a-a60d41a95b9c
	sqlite> select * from Item;
	bbbbf32f-c708-4002-adfb-85c0a8fc0a24|N|Hola|
	21d2cbcd-4998-49e8-9b6c-bdca81482f8b|N|Hola|
	db1becae-0ffc-4f16-b83a-a60d41a95b9c|P|Hola|
	cf69da14-3824-48ed-8c52-1bd7d7104c1c|P|Hola|
	979c90d8-15f1-44e2-bad0-da217ed4ea66|P|Otro hola|
	sqlite> .quit
	$
