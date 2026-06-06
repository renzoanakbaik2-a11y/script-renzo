local senjata = script.Parent
local tempat_keluar_peluru = senjata.Muzzle
local jarak_maksimal = 50
local saklar_aktif = false

game.ReplicatedStorage.SinyalAutoAim.OnServerEvent:Connect(function(player, status_baru)
    saklar_aktif = status_baru
end)

while true do
    if saklar_aktif == true then
        local target_terdekat = nil
        local jarak_terpendek = jarak_maksimal

        for _, objek in pairs(game.Workspace:GetChildren()) do
            if objek:FindFirstChild("Humanoid") and objek.Name == "Zombie" then
                local jarak = (senjata.Position - objek.HumanoidRootPart.Position).Magnitude
                
                if jarak < jarak_terpendek then
                    jarak_terpendek = jarak
                    target_terdekat = objek 
                end
            end
        end

        if target_terdekat then
            local posisi_musuh = target_terdekat.HumanoidRootPart.Position
            
            senjata.CFrame = CFrame.new(senjata.Position, posisi_musuh)
            
            local peluru = Instance.new("Part")
            peluru.Size = Vector3.new(0.5, 0.5, 2)
            peluru.Color = Color3.fromRGB(255, 255, 0)
            peluru.CFrame = tempat_keluar_peluru.CFrame
            peluru.Parent = game.Workspace
            
            local kecepatan = Instance.new("BodyVelocity")
            kecepatan.Velocity = senjata.CFrame.LookVector * 100
            kecepatan.Parent = peluru
            
            game.Debris:AddItem(peluru, 2)
        end
    end

    task.wait(0.2) 
end

local tombol = script.Parent
local status = false

local function saatDiklik()
    status = not status
    
    if status == true then
        tombol.Text = "AUTO AIM: ON"
        tombol.BackgroundColor3 = Color3.fromRGB(0, 255, 0)
        game.ReplicatedStorage.SinyalAutoAim:FireServer(true)
    else
        tombol.Text = "AUTO AIM: OFF"
        tombol.BackgroundColor3 = Color3.fromRGB(255, 0, 0)
        game.ReplicatedStorage.SinyalAutoAim:FireServer(false)
    end
end

tombol.MouseButton1Click:Connect(saatDiklik)
