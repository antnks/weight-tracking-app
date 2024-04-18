<?php

if (!isset($_GET["s"]) || !isset($_GET["v"]))
    die();

$file = "m";
if ($_GET["s"] == "f")
    $file = "f";

file_put_contents ($file.".txt", date('Y-m-d H:i:s').",".$_GET["v"].PHP_EOL, FILE_APPEND);

?>
