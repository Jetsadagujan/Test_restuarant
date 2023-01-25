# @startuml
# title Employee: Management
# actor Hr as hr #ffcc88
# entity hr_api #fe050e
# database employee #d5e9dc
#
# hr -> hr_api: POST/api/employee
#
# activate hr
#   activate hr_api
#       hr_api -> employee : insert into employee
#       employee -> hr_api : success
#       hr_api -> hr: response
#   deactivate hr_api
# deactivate hr
#
#
# @enduml
@startuml
title Restuarant: Management
actor "customer" #deepskyblue

actor "waitress" as waitress #ffcc88

control "waitress-api" #ffcc88

database "order" #ffcc88


control "kitchen-api" #Salmon

actor "kitchen" as kitchen #Salmon



activate "customer"
customer -> waitress: order a menu
deactivate "customer"

activate "waitress"
waitress -> "waitress-api": POST /res/insert/oder
activate "waitress-api"
"waitress-api" -> order: insert into bill_management (bill_no,create_by,bill_date,update_by,status = received)
"waitress-api" -> order: insert into order_management (bill_no,order_id,menu)
"order" ->  "waitress-api": success
"waitress" <-  "waitress-api": success
deactivate "waitress-api"
deactivate "waitress"
activate "kitchen"
kitchen -> "kitchen-api" : POST /res/get/oder
activate "kitchen-api"
"kitchen-api" -> order: payload = { "status" : "received"}
order -> "kitchen-api": response
"kitchen-api" -> kitchen: response List<{ billNo,tableNumber,createdBy,updatedBy ,cookingBy,billDate,status}>
deactivate "kitchen-api"
kitchen -> "kitchen-api" : POST /res/update/oder/status
activate "kitchen-api"
"kitchen-api" -> order: payload = { "id","cookingBy","status" : "cooking"}
order -> "kitchen-api": success
"kitchen-api" -> kitchen: response
deactivate "kitchen-api"
kitchen -> kitchen: cooking
kitchen -> "kitchen-api" : POST /res/update/oder/status
activate "kitchen-api"
"kitchen-api" -> order: payload = {"id","cookingBy", "status" : "cooked"}
order -> "kitchen-api": success
"kitchen-api" -> kitchen: response
deactivate "kitchen-api"

deactivate "kitchen"
activate "waitress"
waitress -> "waitress-api" : POST /res/get/oder
activate "waitress-api"
"waitress-api" -> order: payload = { "status" : "cooked"}
order -> "waitress-api": response
"waitress-api" -> waitress: response
deactivate "waitress-api"

waitress -> "waitress-api" : POST /res/update/oder/status
activate "waitress-api"
"waitress-api" -> order: payload = {"id","waitressBy", "status" : "served"}
order -> "waitress-api": response
"waitress-api" -> waitress: response
deactivate "waitress-api"
waitress -> customer: serve a plate
waitress -> "waitress-api" : POST /res/update/oder/status
activate "waitress-api"
"waitress-api" -> order: payload = {"id","waitressBy", "status" : "done"}
order -> "waitress-api": response
"waitress-api" -> waitress: response
deactivate "waitress-api"
deactivate "waitress"


@enduml