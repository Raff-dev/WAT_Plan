# Generated by Django 3.0.2 on 2020-04-19 10:41

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('home', '0002_auto_20200419_1155'),
    ]

    operations = [
        migrations.AddField(
            model_name='apk',
            name='release_date',
            field=models.DateField(default=datetime.date(2020, 4, 19)),
            preserve_default=False,
        ),
    ]