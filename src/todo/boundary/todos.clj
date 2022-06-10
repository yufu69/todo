(ns todo.boundary.todos
  (:require [clojure.java.jdbc :as jdbc]))

(defprotocol Todos
  (get-todos [db])
  (find-todo [db id])
  (create-todo [db params])
  (update-todo [db id params])
  (delete-todo [db id])
  )

(extend-protocol Todos
  duct.database.sql.Boundary

  (get-todos [{:keys [spec]}]
    (jdbc/query spec ["SELECT * FROM todos"]))

  (find-todo [{:keys [spec]} id]
    (jdbc/query spec ["SELECT * FROM todos WHERE id = ?" id]))

  ;; "No such var: jdbc/get-by-id" ってコンパイルエラーが出る
  ;; (find-todo [{:keys [spec]} id]
  ;;   (jdbc/get-by-id spec :todos id))

  (create-todo [{:keys [spec]} params]
    (jdbc/insert! spec :todos {:title (:title params)}))

  (update-todo [{:keys [spec]} id params]
    (jdbc/update! spec :todos {:title (:title params)} ["id = ?" id]))

  (delete-todo [{:keys [spec]} id]
    (jdbc/delete! spec :todos ["id = ?" id]))
  
  )
