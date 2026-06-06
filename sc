local senjata = script.Parent
local tempat_keluar_peluru = senjata.Muzzle
local jarak_maksimal = 50
local saklar_aktif = false

-- Menerima sinyal ON/OFF dari tombol player
game.ReplicatedStorage.SinyalAutoAim.OnServerEvent:Connect(function(player, status_baru)
    saklar_aktif = status_baru
end)

while true do
    -- Cek dulu apakah tombolnya lagi ON? Kalau iya, baru jalanin auto aim
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
