# Generated by Django 3.0.2 on 2020-04-19 10:47

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('home', '0003_apk_release_date'),
    ]

    operations = [
        migrations.AlterField(
            model_name='apk',
            name='release_date',
            field=models.DateField(blank=True),
        ),
    ]